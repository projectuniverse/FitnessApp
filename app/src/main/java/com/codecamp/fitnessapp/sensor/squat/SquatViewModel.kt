package com.codecamp.fitnessapp.sensor.squat

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SquatViewModel @Inject constructor(
    private val squatRepository: SquatRepository,
    val squatUtil: SquatUtil
): ViewModel() {
    val accelerometerData = squatRepository.accelerometerData
    //val gyroscopeData = squatRepository.gyroscopeData

    /*fun updateRepetitions(): Int {
        return squatUtil.checkSquat(accelerometerData.value)
    }*/

    fun startListening() {
        squatRepository.startListening()
    }

    fun stopListening() {
        squatRepository.stopListening()
    }
}