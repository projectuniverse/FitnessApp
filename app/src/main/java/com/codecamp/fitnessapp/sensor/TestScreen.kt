package com.codecamp.fitnessapp.sensor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codecamp.fitnessapp.sensor.pushup.SensorViewModel

@Composable
fun TestScreen(
    sensorViewModel: SensorViewModel = hiltViewModel()
) {
    val proxVM = sensorViewModel.proximitySensorData.collectAsState().value
    //val proxPU = sensorViewModel.pushupUtil.x.collectAsState().value
    //val rep = sensorViewModel.repetitions.collectAsState().value
    //val test = sensorViewModel.test.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        //Text(text = "$test")
        Text(text = "Repetitions: ${sensorViewModel.updateRepetitions() }", fontSize = 30.sp)
        //Text(text = "Prox. Data (PU): ${proxPU.toInt()}")
        Text(text = "Prox. Sensor(VM): ${proxVM.toInt()}", fontSize = 40.sp)
        Button(onClick = { sensorViewModel.start() }) {
            Text(text = "Start")
        }
        Button(onClick = { sensorViewModel.stop() }) {
            Text(text = "Stop")
        }
    }
}