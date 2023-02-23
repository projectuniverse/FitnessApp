package com.codecamp.fitnessapp.sensor

import kotlinx.coroutines.flow.MutableStateFlow

interface InsideWorkoutRepositoryInterface {
    val proximitySensorData: MutableStateFlow<Float>
    fun startListening()
    fun stopListening()
}