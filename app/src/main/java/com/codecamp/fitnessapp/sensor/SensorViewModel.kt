package com.codecamp.fitnessapp.sensor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val insideWorkoutRepository: InsideWorkoutRepositoryInterface
): ViewModel() {
    private var proximitySensor = insideWorkoutRepository.getProximitySensor()

    var proximity by mutableStateOf(0.0f)

    init {
        proximitySensor.setOnValueChange {values ->
            proximity = values[0]
        }
        proximitySensor.startListening()
    }
}