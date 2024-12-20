package com.HyperCoder57.Clima.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun WeatherCard(
    state: WeatherState,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    state.weatherInfo?.currentWeatherData?.let { data ->
        val timeFormatted = data.time.format(DateTimeFormatter.ofPattern("HH:mm"))
        val temperature = "${data.temperatureCelsius}°C"
        val weatherDesc = data.weatherType.weatherDesc

        val pressure = data.pressure.roundToInt()
        val humidity = data.humidity.roundToInt()
        val windSpeed = data.windSpeed.roundToInt()

        // Precalcular los iconos para evitar repeticiones
        val pressureIcon = ImageVector.vectorResource(id = R.drawable.ic_pressure)
        val humidityIcon = ImageVector.vectorResource(id = R.drawable.ic_drop)
        val windIcon = ImageVector.vectorResource(id = R.drawable.ic_wind)

        Card(
            backgroundColor = backgroundColor,
            shape = RoundedCornerShape(10.dp),
            modifier = modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Hora actual
                Text(
                    text = "Today $timeFormatted",
                    modifier = Modifier.align(Alignment.End),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Icono del clima
                Image(
                    painter = painterResource(id = data.weatherType.iconRes),
                    contentDescription = null,
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Temperatura
                Text(
                    text = temperature,
                    fontSize = 50.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Descripción del clima
                Text(
                    text = weatherDesc,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(32.dp))

                // Datos adicionales del clima (presión, humedad, viento)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherDataDisplay(
                        value = pressure,
                        unit = "hpa",
                        icon = pressureIcon,
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = humidity,
                        unit = "%",
                        icon = humidityIcon,
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = windSpeed,
                        unit = "km/h",
                        icon = windIcon,
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                }
            }
        }
    }
}
