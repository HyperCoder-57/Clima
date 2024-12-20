package com.HyperCoder57.Clima.domain.repository

import com.HyperCoder57.Clima.domain.util.Resource
import com.HyperCoder57.Clima.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>
}