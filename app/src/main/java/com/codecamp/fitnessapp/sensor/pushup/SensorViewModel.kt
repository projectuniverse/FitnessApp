package com.codecamp.fitnessapp.sensor.pushup

import androidx.lifecycle.ViewModel
import com.codecamp.fitnessapp.sensor.pushup.PushUpUtil
import com.codecamp.fitnessapp.sensor.situp.SitUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(
    //private val insideWorkoutRepository: PushUpRepository,
    private val insideWorkoutRepository: SitUpRepository,
    val pushupUtil: PushUpUtil
): ViewModel() {
    //val proximitySensorData = insideWorkoutRepository.proximitySensorData
    val proximitySensorData = insideWorkoutRepository.gyroscopeData
    //val test = MutableStateFlow(0)

    val repetitions = pushupUtil.repetitions

    fun updateRepetitions(): Int {
        return pushupUtil.checkPushUp(proximitySensorData.value)
    }

    fun startListening() {
        insideWorkoutRepository.startListening()

    }

    fun stopListening() {
        insideWorkoutRepository.stopListening()
    }
}