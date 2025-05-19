package com.example.appsectorsalud

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class IniciarSesion : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnIniciar: Button
    private lateinit var btnRegistrarse: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciar_sesion)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnIniciar = findViewById(R.id.btn_iniciar)
        btnRegistrarse = findViewById(R.id.btn_registrarse)

        btnIniciar.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            if (userId != null) {
                                val dbRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(userId)
                                dbRef.get().addOnSuccessListener { snapshot ->
                                    val fechaNacimientoStr = snapshot.child("fechaNacimiento").value?.toString()

                                    if (fechaNacimientoStr != null) {
                                        try {
                                            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                            val fechaNacimiento = formato.parse(fechaNacimientoStr)

                                            val hoy = Calendar.getInstance()
                                            val nacimiento = Calendar.getInstance()
                                            nacimiento.time = fechaNacimiento!!

                                            var edad = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR)
                                            if (hoy.get(Calendar.DAY_OF_YEAR) < nacimiento.get(Calendar.DAY_OF_YEAR)) {
                                                edad--
                                            }

                                            if (edad >= 18) {
                                                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                                                startActivity(Intent(this, ExpedienteActivity::class.java))
                                            } else {
                                                Toast.makeText(this, "Menor de edad. Requiere verificación del tutor.", Toast.LENGTH_SHORT).show()
                                                startActivity(Intent(this, HuellaTutor::class.java))
                                            }
                                            finish()

                                        } catch (e: Exception) {
                                            Log.e("Edad", "Error al analizar fecha de nacimiento", e)
                                            Toast.makeText(this, "Error al leer fecha de nacimiento", Toast.LENGTH_LONG).show()
                                        }
                                    } else {
                                        Toast.makeText(this, "No se encontró la fecha de nacimiento", Toast.LENGTH_LONG).show()
                                    }
                                }.addOnFailureListener {
                                    Toast.makeText(this, "Error al obtener datos del usuario", Toast.LENGTH_LONG).show()
                                }
                            }
                        } else {
                            Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }

        btnRegistrarse.setOnClickListener {
            val intent = Intent(this, RegistrarseActivity::class.java)
            startActivity(intent)
        }

        rabbitIniciarConsumo()
    }

    fun rabbitIniciarConsumo() {
        RabbitMQConsumer.iniciarConsumo { rawMsg ->
            runOnUiThread {
                try {
                    val json = JSONObject(rawMsg)

                    val tipo = json.optString("tipo")
                    if (tipo.isNullOrEmpty()) {
                        Log.e("RabbitMQ", "Mensaje sin campo 'tipo': $rawMsg")
                        return@runOnUiThread
                    }

                    val pacienteUuid = json.optString("pacienteUuid")

                    if (pacienteUuid.isEmpty()) {
                        Log.e("RabbitMQ", "Sin pacienteUuid y sin usuario autenticado")
                        return@runOnUiThread
                    }

                    val data: Map<String, Any>? = when (tipo) {

                        "SolicitudExpediente" -> mapOf(
                            "cedulaProfesional" to json.optString("cedulaProfesional"),
                            "nombre" to json.optString("nombre"),
                            "tipo" to tipo,
                            "timestamp" to com.google.firebase.database.ServerValue.TIMESTAMP
                        )

                        else -> {
                            Log.w("RabbitMQ", "Tipo desconocido: $tipo")
                            null
                        }
                    }

                    data?.let {
                        val ref = FirebaseDatabase.getInstance()
                            .getReference("Usuarios")
                            .child(pacienteUuid)
                            .child("mensajes")

                        ref.push().setValue(it)
                            .addOnSuccessListener { Log.d("RealtimeDB", "Mensaje guardado") }
                            .addOnFailureListener { e ->
                                Log.e(
                                    "RealtimeDB",
                                    "Error al guardar",
                                    e
                                )
                            }
                    }

                } catch (e: Exception) {
                    Log.e("RabbitMQ", "Error procesando JSON", e)
                }
            }
        }
    }
}
