package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.Weather

@Composable
fun WeatherScreen(
    //weatherViewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.Factory)
) {

    val weather = Weather(0,"", 0.0, 0.0, 0, 0 ,0, "", "")

    Column(
        modifier = Modifier.fillMaxWidth(0.7f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherInfos(weather)

        Spacer(modifier = Modifier.height(8.dp))


        if (5 != -1) {//TODO extra in weather das sagt ob warnung halt ne Uhrzeit
            WeatherWarning(time = 5)
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
            Text(text = weather.temperature.toString() + " °C", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = weather.windSpeed.toString() + " km/h", fontSize = 20.sp)
        }
    }
}

@Composable
fun WeatherWarning(time: Int) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.width(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = "",
                modifier = Modifier.size(32.dp),
                tint = Color.Red
            )
        }

        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "Bad Weather at " + time + "pm")
    }
}
