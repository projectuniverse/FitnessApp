package com.codecamp.fitnessapp.sensor

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SensorViewModel @Inject constructor(
    private val insideWorkoutRepository: DefaultInsideWorkoutRepository
): ViewModel() {

    fun fetchSensorData() {
        // get SensorData from Repo
    }


}