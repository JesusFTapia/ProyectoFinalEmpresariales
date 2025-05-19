package com.example.appsectorsalud.data

data class MensajeAgendar(
    val idPaciente: String,
    val nombre: String,
    val idProfesional: String,
    val tipo: String,
    val fecha: String,
    val jwt: String


) {

    fun toJson(): String {
        return com.google.gson.Gson().toJson(this)
    }
}
