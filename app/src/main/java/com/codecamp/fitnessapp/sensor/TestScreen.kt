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
    val proxVM = sensorViewModel.proximitySensorData.collectAsState().value
    val proxPU = sensorViewModel.pushupUtil.x.collectAsState().value
    val rep = sensorViewModel.repetitions.collectAsState().value
    //val test = sensorViewModel.test.collectAsState().value

    Column() {
        //Text(text = "$test")
        Text(text = "Repetitions: $rep")
        Text(text = "Prox. Data (PU): ${proxPU.toInt()}")
        Text(text = "Prox. Sensor(VM): ${proxVM.toInt()}")
        Button(onClick = { sensorViewModel.start() }) {
            Text(text = "Start")
        }
        Button(onClick = { sensorViewModel.stop() }) {
            Text(text = "Stop")
        }
    }
}