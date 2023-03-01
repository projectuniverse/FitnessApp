package com.codecamp.fitnessapp.sensor.pushup

import kotlinx.coroutines.flow.MutableStateFlow

interface PushUpRepository {
    val proximitySensorData: MutableStateFlow<Float>
    fun startListening()
    fun stopListening()
}