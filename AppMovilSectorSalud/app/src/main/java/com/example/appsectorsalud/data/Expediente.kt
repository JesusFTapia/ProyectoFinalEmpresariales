package com.example.appsectorsalud.data

data class Expediente(
    val fechaCreacion: String,
    val alergias: List<String>,
    val diagnosticos: List<String>,
    val vacunas: List<String>,
    val radiografias: List<String>,
    val notasAdicionales: String
)