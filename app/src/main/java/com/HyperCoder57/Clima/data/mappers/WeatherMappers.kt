package com.HyperCoder57.Clima.data.mappers

import com.HyperCoder57.Clima.data.remote.WeatherDataDto
import com.HyperCoder57.Clima.data.remote.WeatherDto
import com.HyperCoder57.Clima.domain.weather.WeatherData
import com.HyperCoder57.Clima.domain.weather.WeatherInfo
import com.HyperCoder57.Clima.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// Mantiene el índice y los datos del clima
private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

// Convierte WeatherDataDto a un mapa de WeatherData agrupado por días
fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),  // Convierte el tiempo
                temperatureCelsius = temperatures[index],  // Temperatura
                pressure = pressures[index],  // Presión
                windSpeed = windSpeeds[index],  // Velocidad del viento
                humidity = humidities[index],  // Humedad
                weatherType = WeatherType.fromWMO(weatherCodes[index])  // Tipo de clima
            )
        )
    }.groupBy {
        it.index / 24  // Agrupa por días
    }.mapValues {
        it.value.map { it.data }  // Extrae los datos de clima
    }
}

// Convierte WeatherDto en WeatherInfo con datos por día y el clima actual
fun WeatherDto.toWeatherInfo(): WeatherInfo {
    val weatherDataMap = weatherData.toWeatherDataMap()  // Mapa de datos por día

    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if(now.minute < 30) now.hour else now.hour + 1  // Hora aproximada
        it.time.hour == hour  // Compara la hora
    }

    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,  // Datos por día
        currentWeatherData = currentWeatherData  // Clima actual
    )
}
