package com.example.appsectorsalud

import com.example.appsectorsalud.data.Expediente
import com.example.appsectorsalud.data.PermisoRequest
import com.example.appsectorsalud.data.PermisoResponse
import com.example.appsectorsalud.data.Profesional
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("api/nuevopaciente/")
    fun enviarPaciente(@Body nuevoPaciente: NuevoPaciente): Call<Void>

    @POST("api/generarAcceso/")
    fun enviarRespuesta(@Body respuesta: PermisoRequest): Call<PermisoResponse>

    @GET("api/expedientes/{pacienteId}")
    fun getExpedientePorId(@Path("pacienteId") pacienteId: String): Call<Expediente>

    @GET("api/profesionales")
    fun getProfesionales(): Call<List<Profesional>>

}