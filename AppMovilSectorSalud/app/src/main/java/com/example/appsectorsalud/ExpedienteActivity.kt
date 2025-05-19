package com.example.appsectorsalud

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import com.example.appsectorsalud.data.Expediente
import com.example.appsectorsalud.databinding.ActivityExpedienteBinding
import com.example.appsectorsalud.utils.RadiografiasAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ExpedienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpedienteBinding
    private lateinit var patientIdTextView: TextView
    private lateinit var nombreTextView: TextView
    private lateinit var creationDateTextView: TextView
    private lateinit var alergiasList: RecyclerView
    private lateinit var radiografiasList: RecyclerView
    private lateinit var diagnosticosList: RecyclerView
    private lateinit var vacunasList: RecyclerView
    private lateinit var notasList: RecyclerView

    // Define the ActivityResultLauncher to handle the result from CitaActivity
    private lateinit var citaActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExpedienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnCerrarSesion = findViewById<AppCompatButton>(R.id.btnCerrarSesion)
        btnCerrarSesion.setOnClickListener {
            cerrarSesion()
        }

        patientIdTextView = findViewById(R.id.patientId)
        nombreTextView = findViewById(R.id.nombre)
        creationDateTextView = findViewById(R.id.creationDate)
        alergiasList = findViewById(R.id.alergiasList)
        notasList = findViewById(R.id.notasList)
        radiografiasList = findViewById(R.id.radiografiasList)
        vacunasList = findViewById(R.id.vacunasList)
        diagnosticosList = findViewById(R.id.diagnosticosList)

        vacunasList.layoutManager = LinearLayoutManager(this)
        radiografiasList.layoutManager = LinearLayoutManager(this)
        diagnosticosList.layoutManager = LinearLayoutManager(this)
        alergiasList.layoutManager = LinearLayoutManager(this)
        notasList.layoutManager = LinearLayoutManager(this)

        // Set up the ActivityResultLauncher to handle the result
        citaActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // If the result is OK, reload the data
                loadExpediente()
            }
        }

        // Set up the button to open CitaActivity
        binding.btnCitas.setOnClickListener {
            val intent = Intent(this, CitaActivity::class.java)
            citaActivityResultLauncher.launch(intent)
        }

        binding.btnNotificaciones.setOnClickListener{
            val intent = Intent(this, NotificacionesActivity::class.java)
            startActivity(intent)
        }

        // Initial data load
        loadExpediente()
    }

    private fun loadExpediente() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid

        if (uid == null) {
            Log.e("FirebaseAuth", "Usuario no autenticado")
            return
        }

        val database = FirebaseDatabase.getInstance()
        val usuarioRef = database.getReference("Usuarios").child(uid)

        usuarioRef.get().addOnSuccessListener { snapshot ->
            val curp = snapshot.child("curp").getValue(String::class.java) ?: "CURP no disponible"
            val nombres = snapshot.child("nombres").getValue(String::class.java) ?: ""
            val apellidoPaterno = snapshot.child("apellidoPaterno").getValue(String::class.java) ?: ""
            val apellidoMaterno = snapshot.child("apellidoMaterno").getValue(String::class.java) ?: ""

            val nombreCompleto = "$nombres $apellidoPaterno $apellidoMaterno".trim()

            ApiClient.instance.getExpedientePorId(uid).enqueue(object : Callback<Expediente> {
                override fun onResponse(call: Call<Expediente>, response: Response<Expediente>) {
                    if (response.isSuccessful) {
                        val expediente = response.body()
                        expediente?.let {
                            patientIdTextView.text = curp
                            nombreTextView.text = if (nombreCompleto.isNotBlank()) nombreCompleto else "Nombre no disponible"
                            // Si es tipo String con formato completo, como "2025-05-12T08:58:37"
                            val rawDate = it.fechaCreacion ?: "Fecha no disponible"
                            val soloFecha = rawDate.split("T").firstOrNull() ?: rawDate
                            creationDateTextView.text = soloFecha

                            alergiasList.adapter = SimpleListAdapter(it.alergias ?: listOf("Sin datos"))
                            radiografiasList.adapter = if (!it.radiografias.isNullOrEmpty()) {
                                RadiografiasAdapter(it.radiografias)
                            } else {
                                SimpleListAdapter(listOf("Sin datos"))
                            }

                            diagnosticosList.adapter = SimpleListAdapter(it.diagnosticos ?: listOf("Sin datos"))
                            vacunasList.adapter = SimpleListAdapter(it.vacunas ?: listOf("Sin datos"))
                            notasList.adapter = SimpleListAdapter(listOf(it.notasAdicionales ?: "Sin notas"))
                        }
                    } else {
                        Log.e("API_ERROR", "Código de error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<Expediente>, t: Throwable) {
                    Log.e("API_FAILURE", "Error: ${t.message}")
                }
            })
        }.addOnFailureListener {
            Log.e("FirebaseDB", "Error al obtener datos del usuario: ${it.message}")
        }
    }

    private fun cerrarSesion() {
        FirebaseAuth.getInstance().signOut()  // Cierra sesión

        val intent = Intent(this, IniciarSesion::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }


    class SimpleListAdapter(private val items: List<String>) :
        RecyclerView.Adapter<SimpleListAdapter.ViewHolder>() {

        class ViewHolder(val view: TextView) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val textView = TextView(parent.context).apply {
                setPadding(16, 16, 16, 16)
                textSize = 14f
            }
            return ViewHolder(textView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.view.text = items[position]
        }

        override fun getItemCount(): Int = items.size
    }
}
