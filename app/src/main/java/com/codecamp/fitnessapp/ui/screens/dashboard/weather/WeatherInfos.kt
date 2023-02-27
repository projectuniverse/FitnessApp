package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.Weather
import java.math.RoundingMode

@Composable
fun WeatherInfos(weather: Weather) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.width(128.dp),
            verticalArrangement = Arrangement.Center
        ) {
            val weatherIcon = when (weather.weatherType) {
                "Clear" -> {
                    if (weather.time >= weather.sunrise && weather.time < weather.sunset) {
                        painterResource(id = R.drawable.sunny)
                    } else {
                        painterResource(id = R.drawable.moon)
                    }
                }
                "Clouds" -> painterResource(id = R.drawable.cloudy)
                "Rain" -> painterResource(id = R.drawable.rainy)
                "Drizzle" -> {
                    if (weather.time >= weather.sunrise && weather.time < weather.sunset) {
                        painterResource(id = R.drawable.partly_cloudy_day)
                    } else {
                        painterResource(id = R.drawable.partly_cloudy_night)
                    }
                }
                "Snow" -> painterResource(id = R.drawable.snowing)
                "Thunderstorm" -> painterResource(id = R.drawable.thunderstorm)
                else -> painterResource(id = R.drawable.foggy)
            }

            Icon(
                painter = weatherIcon,
                contentDescription = "",
                modifier = Modifier.size(128.dp),
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.width(5.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = (weather.temperature - 273.15).toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString() + " ℃",
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = (weather.windSpeed * 3.6).toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString() + " km/h",
                fontSize = 20.sp
            )
        }
    }
}