package com.codecamp.fitnessapp.sensor.pushup

import com.codecamp.fitnessapp.sensor.ProximitySensor
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class PushUpRepositoryImpl @Inject constructor(
    private val proximitySensor: ProximitySensor,
): PushUpRepository {
    override val proximitySensorData: MutableStateFlow<Float> = MutableStateFlow(2.0f)

    override fun startListening() {
        proximitySensor.startListening {values ->
            proximitySensorData.value = values[0]
        }
    }

    override fun stopListening() {
        proximitySensor.stopListening()
    }
}