package com.codecamp.fitnessapp.sensor.squat

import kotlinx.coroutines.flow.MutableStateFlow

interface SquatRepository {
    val accelerometerData: MutableStateFlow<List<Float>>

    fun startListening()

    fun stopListening()
}