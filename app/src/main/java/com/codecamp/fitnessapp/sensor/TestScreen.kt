package com.codecamp.fitnessapp.sensor

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TestScreen(
    sensorViewModel: SensorViewModel = hiltViewModel()
) {
    val dis = sensorViewModel.proximity
   Text(text = "$dis")
}