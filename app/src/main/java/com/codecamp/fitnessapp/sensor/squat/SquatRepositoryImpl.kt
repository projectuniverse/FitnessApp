package com.codecamp.fitnessapp.sensor.squat

import com.codecamp.fitnessapp.sensor.Accelerometer
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SquatRepositoryImpl @Inject constructor(
    private val accelerometer: Accelerometer
): SquatRepository {
    override val accelerometerData: MutableStateFlow<List<Float>> = MutableStateFlow(mutableListOf(0.0f, 0.0f, 0.0f))

    override fun startListening() {
        accelerometer.startListening { values ->
            accelerometerData.value = values
        }
    }

    override fun stopListening() {
        accelerometer.stopListening()
    }
}