package com.example.appsectorsalud

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView

class HuellaTutor : AppCompatActivity() {

    private lateinit var btnVerificarHuella: AppCompatImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_huella_tutor)

        btnVerificarHuella = findViewById(R.id.btnLogin)

        btnVerificarHuella.setOnClickListener {
            Toast.makeText(this, "Huella verificada correctamente", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ExpedienteActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
