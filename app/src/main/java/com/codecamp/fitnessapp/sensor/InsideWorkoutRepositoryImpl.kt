package com.codecamp.fitnessapp.sensor

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class InsideWorkoutRepositoryImpl @Inject constructor(
    private val proximitySensor: ProximitySensor,
): InsideWorkoutRepositoryInterface {
    override val proximitySensorData: MutableStateFlow<Float> = MutableStateFlow(0.0f)

    override fun startListening() {
        proximitySensor.startListening {values ->
            proximitySensorData.value = values[0]
        }
    }

    override fun stopListening() {
        proximitySensor.stopListening()
    }
}