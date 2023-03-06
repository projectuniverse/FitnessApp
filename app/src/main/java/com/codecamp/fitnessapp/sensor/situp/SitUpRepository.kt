package com.codecamp.fitnessapp.sensor.situp

import kotlinx.coroutines.flow.MutableStateFlow

interface SitUpRepository {
    val gyroscopeData: MutableStateFlow<List<Float>>

    fun startListening()

    fun stopListening()
}