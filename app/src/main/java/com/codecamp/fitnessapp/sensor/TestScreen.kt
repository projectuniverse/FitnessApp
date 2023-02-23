package com.codecamp.fitnessapp.sensor

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TestScreen(
    sensorViewModel: SensorViewModel = hiltViewModel()
) {
    val dis = sensorViewModel.proximitySensorData.collectAsState().value
    //val rep = sensorViewModel.checkState()
    Column() {
        //Text(text = "Rep: $rep")
        Text(text = "Prox. Sensor: ${dis.toInt()}")
        Button(onClick = { sensorViewModel.start() }) {
            Text(text = "Start")
        }
        Button(onClick = { sensorViewModel.stop() }) {
            Text(text = "Stop")
        }
    }
}