package com.codecamp.fitnessapp.ui.screens.dashboard.weather

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codecamp.fitnessapp.ui.screens.dashboard.WeatherInfos
import com.codecamp.fitnessapp.ui.screens.dashboard.WeatherViewModel
import com.google.accompanist.permissions.*

/*
* The current weather and a forecast is displayed. If no location permissions are granted,
* a button is shown to give the needed permissions.
* If no weather is able to retrieve from the API
* or loaded from the repository, a warning is shown instead.
* */
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherLabel(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val weather = viewModel.weather.collectAsState(initial = null).value

    val locationPermission = rememberMultiplePermissionsState(
        permissions = listOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION),
        onPermissionsResult = {
            viewModel.fetchWeather()
        }
    )

    if (locationPermission.allPermissionsGranted) {
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
    } else {
        LocationPermission(locationPermission)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermission(permissionState: MultiplePermissionsState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Missing Permissions")
            Text(text = "Grant access to your location?")
        }
        Spacer(modifier = Modifier.padding(20.dp))
        Button(onClick = {
            permissionState.launchMultiplePermissionRequest()
        }) {
            Text(text = "Yes")
        }
    }
}
