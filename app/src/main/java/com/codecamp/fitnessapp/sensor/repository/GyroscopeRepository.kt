package com.codecamp.fitnessapp.sensor.repository

import kotlinx.coroutines.flow.MutableStateFlow

interface GyroscopeRepository {
    val gyroscopeData: MutableStateFlow<List<Float>>

    fun startListening()

    fun stopListening()
}