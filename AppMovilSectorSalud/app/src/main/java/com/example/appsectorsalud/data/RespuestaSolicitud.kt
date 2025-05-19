package com.example.appsectorsalud.data

import com.google.gson.Gson

data class RespuestaSolicitud(
    val idPaciente: String,
    val nombre: String,
    val idProfesional: String,
    val tipo: String = "RespuestaSolicitud",
    val respuesta: Boolean,
    val fecha_permiso: String,
    val jwt: String
) {
    fun toJson(): String = Gson().toJson(this)
}