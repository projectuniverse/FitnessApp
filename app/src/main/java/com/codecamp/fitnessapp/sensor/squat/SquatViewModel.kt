package com.codecamp.fitnessapp.sensor.squat

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SquatViewModel @Inject constructor(
    private val squatRepository: SquatRepository,
    private val squatUtil: SquatUtil
): ViewModel() {
    val accelerometerData = squatRepository.accelerometerData

    fun updateRepetitions(): Int {
        return squatUtil.checkSquat(accelerometerData.value)
    }

    fun startListening() {
        squatRepository.startListening()
    }

    fun stopListening() {
        squatRepository.stopListening()
    }
}