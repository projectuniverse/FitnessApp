package com.codecamp.fitnessapp.sensor.situp

import com.codecamp.fitnessapp.sensor.Gyroscope
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class SitUpRepositoryImpl @Inject constructor(
    private val gyroscope: Gyroscope
): SitUpRepository {
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