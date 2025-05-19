package com.example.appsectorsalud

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appsectorsalud.data.Notificacion
import com.example.appsectorsalud.data.PermisoRequest
import com.example.appsectorsalud.data.PermisoResponse
import com.example.appsectorsalud.data.RespuestaSolicitud
import com.example.appsectorsalud.databinding.ActivityNotificacionesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale

class NotificacionesActivity : AppCompatActivity() {

    private lateinit var b: ActivityNotificacionesBinding
    private val lista = mutableListOf<Notificacion>()
    private lateinit var adapter: NotificacionesAdapter
    private lateinit var refMensajes: DatabaseReference
    private val uid get() = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityNotificacionesBinding.inflate(layoutInflater)
        setContentView(b.root)

        //‑‑ Recycler
        adapter = NotificacionesAdapter(lista,
            onAccept = { aceptar(it) },
            onReject = { rechazar(it) }
        )
        b.recyclerNotifications.layoutManager = LinearLayoutManager(this)
        b.recyclerNotifications.adapter = adapter

        //‑‑ Firebase
        if (uid == null) {
            Toast.makeText(this, "Inicia sesión primero", Toast.LENGTH_SHORT).show()
            finish(); return
        }
        refMensajes = FirebaseDatabase.getInstance()
            .getReference("Usuarios")
            .child(uid!!)
            .child("mensajes")

        refMensajes.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                lista.clear()
                snapshot.children.forEach { snap ->
                    val n = snap.getValue(Notificacion::class.java) ?: return@forEach
                    if (n.activo) lista.add(n.apply { id = snap.key ?: "" })
                }
                lista.sortByDescending { it.timestamp }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        b.btnBack.setOnClickListener { finish() }
    }

    private fun obtenerNombreCompletoDesdeRealtime(
        uid: String,
        callback: (String) -> Unit
    ) {
        FirebaseDatabase.getInstance()
            .getReference("Usuarios")
            .child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val nom = snapshot.child("nombres").getValue(String::class.java).orEmpty()
                    val pat = snapshot.child("apellidoPaterno").getValue(String::class.java).orEmpty()
                    val mat = snapshot.child("apellidoMaterno").getValue(String::class.java).orEmpty()
                    callback("$nom $pat $mat".trim().ifBlank { "Desconocido" })
                }
                override fun onCancelled(error: DatabaseError) = callback("Desconocido")
            })
    }

    private fun fechaPermisoFormateada(): String {
        val formato = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return formato.format(Date())
    }

    private fun aceptar(n: Notificacion) {
        refMensajes.child(n.id)
            .updateChildren(mapOf("activo" to false, "respuesta" to "aceptado"))

        obtenerNombreCompletoDesdeRealtime(uid!!) { nombreCompleto ->

            val body = PermisoRequest(
                idPaciente      = uid!!,
                idDoctor          = n.cedulaProfesional,
            )

            ApiClient.instance.enviarRespuesta(body).enqueue(object : Callback<PermisoResponse> {
                    override fun onResponse(
                        call: Call<PermisoResponse>,
                        response: Response<PermisoResponse>
                    ) {
                        val fechaVenc = if (response.isSuccessful)
                            response.body()?.fechaVencimiento ?: fechaPermisoFormateada()
                        else
                            fechaPermisoFormateada()

                        val respuesta = RespuestaSolicitud(
                            idPaciente      = uid!!,
                            nombre          = nombreCompleto,
                            idProfesional   = n.cedulaProfesional,
                            respuesta       = true,
                            fecha_permiso   = fechaVenc,
                            jwt             = "mi_token_simulado"
                        )

                        lifecycleScope.launch(Dispatchers.IO) {
                            val ok = RabbitMQSender.enviarMensaje(respuesta.toJson())
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@NotificacionesActivity,
                                    if (ok) "Acceso concedido" else "Error al notificar",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<PermisoResponse>, t: Throwable) {
                        Toast.makeText(
                            this@NotificacionesActivity,
                            "Error de red: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }

    private fun rechazar(n: Notificacion) {
        refMensajes.child(n.id).updateChildren(mapOf("activo" to false, "respuesta" to "rechazado"))

        obtenerNombreCompletoDesdeRealtime(uid!!) { nombreCompleto ->

            val respuesta = RespuestaSolicitud(
                idPaciente       = uid!!,
                nombre           = nombreCompleto,
                idProfesional    = n.cedulaProfesional,
                respuesta        = false,
                fecha_permiso    = fechaPermisoFormateada(),
                jwt              = "mi_token_simulado"
            )

            lifecycleScope.launch(Dispatchers.IO) {
                RabbitMQSender.enviarMensaje(respuesta.toJson())
            }
        }
    }
}