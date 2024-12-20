package com.HyperCoder57.Clima.presentation

import com.HyperCoder57.Clima.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
