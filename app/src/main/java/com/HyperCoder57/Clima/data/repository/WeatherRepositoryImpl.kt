package com.HyperCoder57.Clima.data.repository

import com.HyperCoder57.Clima.data.mappers.toWeatherInfo
import com.HyperCoder57.Clima.data.remote.WeatherApi
import com.HyperCoder57.Clima.domain.repository.WeatherRepository
import com.HyperCoder57.Clima.domain.util.Resource
import com.HyperCoder57.Clima.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
): WeatherRepository {

    // Obtiene los datos meteorológicos y los convierte a WeatherInfo
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(lat = lat, long = long).toWeatherInfo()
            )
        } catch(e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "un error ha ocurrido.")
        }
    }
}
