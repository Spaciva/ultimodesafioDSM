package com.spaciva.aularecursos

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RecursoService {

    // Obtener todos los recursos.
    @GET("recurso")
    fun listarRecursos(): Call<List<Recurso>>

    // Buscar un recurso por su ID.
    @GET("recurso/{id}")
    fun buscarRecurso(
        @Path("id") id: String
    ): Call<Recurso>

    // Guardar un nuevo recurso.
    @POST("recurso")
    fun agregarRecurso(
        @Body recurso: Recurso
    ): Call<Recurso>

    // Modificar un recurso existente.
    @PUT("recurso/{id}")
    fun modificarRecurso(
        @Path("id") id: String,
        @Body recurso: Recurso
    ): Call<Recurso>

    // Eliminar un recurso.
    @DELETE("recurso/{id}")
    fun eliminarRecurso(
        @Path("id") id: String
    ): Call<Recurso>
}