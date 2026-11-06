package com.spaciva.aularecursos

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // La dirección base debe terminar con una barra /.
    private const val BASE_URL =
        "https://6aee3b63a677fe0fc14fd131.mockapi.io/api/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val servicio: RecursoService =
        retrofit.create(RecursoService::class.java)
}