package com.codecamp.fitnessapp.sensor.finall

import kotlinx.coroutines.flow.MutableStateFlow

interface GyroscopeRepository {
    val gyroscropeData: MutableStateFlow<List<Float>>

    fun startListening()

    fun stopListening()
}