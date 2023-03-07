package com.codecamp.fitnessapp.sensor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codecamp.fitnessapp.sensor.pushup.SensorViewModel
import com.codecamp.fitnessapp.sensor.situp.SitUpViewModel
import com.codecamp.fitnessapp.sensor.squat.SquatViewModel

@Composable
fun TestScreen(
    //sensorViewModel: SensorViewModel = hiltViewModel()
    //sensorViewModel: SquatViewModel = hiltViewModel()
    sensorViewModel: SitUpViewModel = hiltViewModel()
) {

    sensorViewModel.checkSitUp()
    Column(modifier = Modifier.fillMaxSize()) {
        //Text(text = "Counter: ${sensorViewModel.squatUtil.counter}")
        //Text(text = "X: ${sensorViewModel.accelerometerData.collectAsState().value[0]}")
        //Text(text = "Y: ${sensorViewModel.accelerometerData.collectAsState().value[1]}")
        //Text(text = "Z: ${sensorViewModel.accelerometerData.collectAsState().value[2]}")
        //Text(text = "Rep: ${sensorViewModel.updateRepetitions()}")
        //Text(text = "State: ${sensorViewModel.squatUtil.state}")
        Text(text = "Counter: ${sensorViewModel.sitUpUtil.counter}")
        Text(text = "RotX: ${sensorViewModel.gyroscopeData.collectAsState().value[0]}")
        Text(text = "RotY: ${sensorViewModel.gyroscopeData.collectAsState().value[1]}")
        Text(text = "RotZ: ${sensorViewModel.gyroscopeData.collectAsState().value[2]}")
        Text(text = "State: ${sensorViewModel.sitUpUtil.state.value.name}")
        Text(text = "Rep: ${sensorViewModel.sitUpUtil.repetition}")
        Button(onClick = {
            sensorViewModel.startListening()
        }) {
            Text(text = "Start")
        }
        Button(onClick = {
            sensorViewModel.stopListening()
        }) {
            Text(text = "Stop")
        }
    }
}