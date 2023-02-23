package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.Weather
import com.codecamp.fitnessapp.ui.screens.ScreenViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val weather = viewModel.weather.collectAsState(initial = null).value

    Column(
        modifier = Modifier.fillMaxWidth(0.7f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (weather != null && viewModel.weatherUpToDate(weather)) {
            WeatherInfos(weather)
            Spacer(modifier = Modifier.height(10.dp))
            WeatherForecast(weather.weatherForecast)
        }
    }
}

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
            Text(text = (weather.temperature-273.1).toString() + " °C", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = weather.windSpeed.toString() + " km/h", fontSize = 20.sp)
        }
    }
}

@Composable
fun WeatherForecast(forecast: String) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.width(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if(!(forecast == "Clear" || forecast == "Clouds")) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = "",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "Forecast: $forecast")
    }
}
