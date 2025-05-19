package com.example.appsectorsalud

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.appsectorsalud.data.MensajeAgendar
import com.example.appsectorsalud.data.Profesional
import com.example.appsectorsalud.databinding.ActivityCitaBinding
import com.example.appsectorsalud.databinding.ActivityExpedienteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCitaBinding
    private val listaDoctores = mutableListOf<Profesional>()
    private lateinit var spinnerAdapter: ArrayAdapter<Profesional>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCitaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.editTextFecha.setOnClickListener { mostrarDatePicker() }

        spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            listaDoctores
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerDoctores.adapter = this
        }

        ApiClient.instance.getProfesionales().enqueue(object: Callback<List<Profesional>> {
            override fun onResponse(
                call: Call<List<Profesional>>,
                response: Response<List<Profesional>>
            ) {
                if (response.isSuccessful) {
                    listaDoctores.clear()
                    listaDoctores.addAll(response.body() ?: emptyList())
                    spinnerAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this@CitaActivity,
                        "Error al cargar doctores: ${response.code()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onFailure(call: Call<List<Profesional>>, t: Throwable) {
                Toast.makeText(
                    this@CitaActivity,
                    "Fallo en conexión: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        // ④ Botones
        binding.btnCita.setOnClickListener { enviarCita() }
        binding.btnRegresar.setOnClickListener { finish() }
    }


    private fun mostrarDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val fechaSeleccionada = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
            binding.editTextFecha.setText(fechaSeleccionada)
        }, year, month, day)

        datePicker.datePicker.minDate = calendar.timeInMillis

        datePicker.show()
    }


    private fun obtenerNombreCompletoDesdeRealtime(uid: String, callback: (String) -> Unit) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(uid)
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nombres = snapshot.child("nombres").getValue(String::class.java) ?: ""
                    val paterno = snapshot.child("apellidoPaterno").getValue(String::class.java) ?: ""
                    val materno = snapshot.child("apellidoMaterno").getValue(String::class.java) ?: ""
                    val nombreCompleto = "$nombres $paterno $materno".trim()
                    callback(nombreCompleto.ifBlank { "Desconocido" })
                } else {
                    callback("Desconocido")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback("Desconocido")
            }
        })
    }

    private fun enviarCita() {
        val fechaTexto = binding.editTextFecha.text.toString()
        val pos = binding.spinnerDoctores.selectedItemPosition
        if (fechaTexto.isEmpty() || pos < 0) {
            Toast.makeText(this, "Selecciona fecha y doctor", Toast.LENGTH_SHORT).show()
            return
        }
        val doctor = listaDoctores[pos]
        val uidPaciente = FirebaseAuth.getInstance().currentUser?.uid
        val nombrePaciente = FirebaseAuth.getInstance().currentUser?.displayName ?: "Desconocido"

        if (fechaTexto.isEmpty() || uidPaciente == null) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val formatoEntrada = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formatoSalida = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        val fechaDate: Date? = try {
            formatoEntrada.parse(fechaTexto)
        } catch (e: ParseException) {
            null
        }

        if (fechaDate == null) {
            Toast.makeText(this, "Formato de fecha inválido", Toast.LENGTH_SHORT).show()
            return
        }

        val fechaFormateada = formatoSalida.format(fechaDate)

        obtenerNombreCompletoDesdeRealtime(uidPaciente) { nombrePaciente ->
            val mensaje = MensajeAgendar(
                idPaciente = uidPaciente,
                nombre = nombrePaciente,
                idProfesional = doctor.cedula,
                tipo = "AgendarCita",
                fecha = fechaFormateada,
                jwt = "mi_token_simulado"
            )

            CoroutineScope(Dispatchers.Main).launch {
                val resultado = RabbitMQSender.enviarMensaje(mensaje.toJson())
                Toast.makeText(this@CitaActivity, if (resultado) "Cita enviada" else "Error al enviar", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }

}
