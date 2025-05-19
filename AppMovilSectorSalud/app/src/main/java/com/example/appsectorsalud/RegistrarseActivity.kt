package com.example.appsectorsalud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date


class RegistrarseActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        val et_nombres: EditText = findViewById(R.id.et_nombres)
        val et_apellido_paterno: EditText = findViewById(R.id.et_apellido_paterno)
        val et_apellido_materno: EditText = findViewById(R.id.et_apellido_materno)
        val et_curp: EditText = findViewById(R.id.et_curp)
        val et_email: EditText = findViewById(R.id.et_email)
        val et_fecha_nacimiento: EditText = findViewById(R.id.et_fecha_nacimiento)
        val et_password: EditText = findViewById(R.id.et_password)

        val btn_sign_in = findViewById(R.id.btn_registrarse) as Button

        et_fecha_nacimiento.setOnClickListener {
            val datePicker = DatePickerFragment()
            datePicker.setOnDateSetListener { _, year, month, dayOfMonth ->
                val fecha = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                et_fecha_nacimiento.setText(fecha)
            }
            datePicker.show(supportFragmentManager, "datePicker")
        }

        btn_sign_in.setOnClickListener {
            val nombres = et_nombres.text.toString()
            val apellidoPaterno = et_apellido_paterno.text.toString()
            val apellidoMaterno = et_apellido_materno.text.toString()
            val curp = et_curp.text.toString()
            val fechaNacimiento = et_fecha_nacimiento.text.toString()
            val email = et_email.text.toString()
            val password = et_password.text.toString()

            if (nombres.isBlank() || apellidoPaterno.isBlank() || apellidoMaterno.isBlank()
                || curp.isBlank() || fechaNacimiento.isBlank() || email.isBlank() || password.isBlank()
            ) {
                Toast.makeText(this, "Todos los campos deben ser llenados", Toast.LENGTH_SHORT)
                    .show()
            } else if (password.length < 6) {
                Toast.makeText(
                    this,
                    "La contraseña debe tener al menos 6 caracteres",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val edad = calcularEdad(fechaNacimiento)

                // Siempre registrar al usuario en Firebase
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val usuario = mapOf(
                                "usuarioId" to user!!.uid,
                                "nombres" to nombres,
                                "apellidoPaterno" to apellidoPaterno,
                                "apellidoMaterno" to apellidoMaterno,
                                "curp" to curp,
                                "fechaNacimiento" to fechaNacimiento,
                                "email" to email
                            )

                            val mensaje = NuevoPaciente(
                                nombre = nombres,
                                apellidoPaterno = apellidoPaterno,
                                apellidoMaterno = apellidoMaterno,
                                id = user.uid,
                                fechaCreacion = Date()
                            )

                            ApiClient.instance.enviarPaciente(mensaje)
                                .enqueue(object : Callback<Void> {
                                    override fun onResponse(
                                        call: Call<Void>,
                                        response: Response<Void>
                                    ) {
                                        if (response.isSuccessful) {
                                            Toast.makeText(
                                                this@RegistrarseActivity,
                                                "Nuevo paciente enviado a la API",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                this@RegistrarseActivity,
                                                "Error al enviar paciente: ${response.code()}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }

                                    override fun onFailure(call: Call<Void>, t: Throwable) {
                                        Toast.makeText(
                                            this@RegistrarseActivity,
                                            "Fallo de conexión: ${t.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                })


                            val database = FirebaseDatabase.getInstance()
                            val reference = database.getReference("Usuarios")
                            reference.child(user.uid).setValue(usuario)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "Usuario registrado correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // Si es menor, va a HuellaTutor. Si no, a ExpedienteActivity
                                    if (edad < 18) {
                                        val intent = Intent(this, HuellaTutor::class.java)
                                        startActivity(intent)
                                    } else {
                                        val intent = Intent(this, ExpedienteActivity::class.java)
                                        startActivity(intent)
                                    }
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        this,
                                        "Error al guardar en Realtime Database",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                        } else {
                            Toast.makeText(
                                this,
                                "El registro falló: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }


    }

    private fun calcularEdad(fechaNacimiento: String): Int {
        val formato = java.text.SimpleDateFormat("dd/MM/yyyy")
        val fechaNac = formato.parse(fechaNacimiento)
        val hoy = java.util.Calendar.getInstance()
        val nacimiento = java.util.Calendar.getInstance()
        nacimiento.time = fechaNac

        var edad = hoy.get(java.util.Calendar.YEAR) - nacimiento.get(java.util.Calendar.YEAR)

        if (hoy.get(java.util.Calendar.DAY_OF_YEAR) < nacimiento.get(java.util.Calendar.DAY_OF_YEAR)) {
            edad--
        }
        return edad
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }

    fun signUp(
        nombres: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        curp: String,
        fechaNacimiento: String,
        email: String,
        password: String
    ) {
        val database = FirebaseDatabase.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val edad = calcularEdad(fechaNacimiento)

                    if (edad < 18) {
                        // ✅ Aquí está el cambio importante: la cuenta ya fue creada, ahora sí pasamos
                        val intent = Intent(this, HuellaTutor::class.java)
                        intent.putExtra("usuarioId", user?.uid)
                        intent.putExtra("nombres", nombres)
                        intent.putExtra("apellidoPaterno", apellidoPaterno)
                        intent.putExtra("apellidoMaterno", apellidoMaterno)
                        intent.putExtra("curp", curp)
                        intent.putExtra("fechaNacimiento", fechaNacimiento)
                        intent.putExtra("email", email)
                        // Puedes guardar la contraseña si realmente la necesitas más adelante (no recomendado)
                        startActivity(intent)
                        finish()
                    } else {
                        // Usuario mayor de edad: guardar en base de datos
                        val usuario = mapOf(
                            "usuarioId" to user!!.uid,
                            "nombres" to nombres,
                            "apellidoPaterno" to apellidoPaterno,
                            "apellidoMaterno" to apellidoMaterno,
                            "curp" to curp,
                            "fechaNacimiento" to fechaNacimiento,
                            "email" to email
                        )

                        val reference = database.getReference("Usuarios")
                        reference.child(user.uid).setValue(usuario)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Usuario registrado correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this, ExpedienteActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(
                                    this,
                                    "Error al guardar en Realtime Database",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                } else {
                    // ⚠️ Aquí manejamos el error real del registro
                    Toast.makeText(
                        this,
                        "El registro falló: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}