package com.codecamp.fitnessapp.sensor.squat

import com.codecamp.fitnessapp.sensor.Accelerometer
import com.codecamp.fitnessapp.sensor.Gyroscope
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SquatRepositoryImpl @Inject constructor(
    private val accelerometer: Accelerometer
    //private val gyroscope: Gyroscope
): SquatRepository {
    override val accelerometerData: MutableStateFlow<List<Float>> = MutableStateFlow(mutableListOf(0.0f, 0.0f, 0.0f))

    //override val gyroscopeData: MutableStateFlow<List<Float>> = MutableStateFlow(mutableListOf(0f, 0f, 0f))

    override fun startListening() {
        accelerometer.startListening { values ->
            accelerometerData.value = values
        }
        /*gyroscope.startListening { values ->
            gyroscopeData.value = values
        }*/
    }

    override fun stopListening() {
        accelerometer.stopListening()
        //gyroscope.stopListening()
    }
}