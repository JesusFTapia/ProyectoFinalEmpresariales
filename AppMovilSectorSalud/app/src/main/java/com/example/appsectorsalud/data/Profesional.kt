package com.example.appsectorsalud.data

data class Profesional(
    val cedula: String,
    val nombre: String
    ){

    fun toJson(): String {
        return com.google.gson.Gson().toJson(this)
    }

    override fun toString(): String{
        return nombre + " - " + cedula
    }
}
