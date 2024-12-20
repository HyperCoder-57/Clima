package com.HyperCoder57.Clima.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    // Obtiene los datos meteorológicos por latitud y longitud
    @GET("v1/forecast?hourly=temperature_2m,weathercode,relativehumidity_2m,windspeed_10m,pressure_msl")
    suspend fun getWeatherData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ): WeatherDto
}
