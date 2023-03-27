package com.codecamp.fitnessapp.sensor.finall

import com.codecamp.fitnessapp.sensor.Gyroscope
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GyroscopeRepositoryImpl @Inject constructor(
    private val gyroscope: Gyroscope
): GyroscopeRepository {
    override val gyroscropeData: MutableStateFlow<List<Float>> = MutableStateFlow(mutableListOf(0f, 0f, 0f))

    override fun startListening() {
        gyroscope.startListening { values ->
            gyroscropeData.value = values
        }
    }

    override fun stopListening() {
        gyroscope.stopListening()
    }
}