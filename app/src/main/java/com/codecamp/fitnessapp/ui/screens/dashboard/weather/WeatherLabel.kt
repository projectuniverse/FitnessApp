package com.codecamp.fitnessapp.ui.screens.dashboard.weather

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codecamp.fitnessapp.ui.screens.dashboard.WeatherInfos
import com.codecamp.fitnessapp.ui.screens.dashboard.WeatherViewModel

@Composable
fun WeatherLabel(
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
        } else {
            WeatherWarning()
        }
    }
}
