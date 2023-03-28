package com.codecamp.fitnessapp.sensor

import com.codecamp.fitnessapp.sensor.repository.GyroscopeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GyroscopeRepositoryImpl @Inject constructor(
    private val gyroscope: Gyroscope
): GyroscopeRepository {
    override val gyroscopeData: MutableStateFlow<List<Float>> = MutableStateFlow(mutableListOf(0f, 0f, 0f))

    override fun startListening() {
        gyroscope.startListening { values ->
            gyroscopeData.value = values
        }
    }

    override fun stopListening() {
        gyroscope.stopListening()
    }
}