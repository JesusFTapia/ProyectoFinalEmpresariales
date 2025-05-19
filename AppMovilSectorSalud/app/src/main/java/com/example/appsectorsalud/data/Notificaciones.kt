package com.example.appsectorsalud.data


data class Notificacion(
    var id: String = "",                 // ← se rellena con la key del nodo
    val tipo: String = "",
    val cedulaProfesional: String = "",
    val nombre: String = "",             // opcional: nombre del doctor
    val timestamp: Long = 0L,
    var activo: Boolean = true
) {
    fun fechaLegible(): String =
        java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
            .format(java.util.Date(timestamp))
}
