package com.example.appsectorsalud

data class CitaRequest(
    val pacienteId: String,
    val doctorId: String,
    val fecha: String
)
