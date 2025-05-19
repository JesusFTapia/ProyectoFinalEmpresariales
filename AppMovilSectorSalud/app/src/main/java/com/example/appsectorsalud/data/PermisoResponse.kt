package com.example.appsectorsalud.data

data class PermisoResponse(
    val idPaciente: String,
    val idDoctor: String,
    val fechaDeGeneracion: String,
    val fechaVencimiento: String
)
