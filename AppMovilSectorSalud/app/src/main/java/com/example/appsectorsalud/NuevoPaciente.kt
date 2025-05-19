package com.example.appsectorsalud

import java.util.Date

data class NuevoPaciente(
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val id: String,
    val fechaCreacion: Date
) {

    fun toJson(): String {
        return com.google.gson.Gson().toJson(this)
    }
}