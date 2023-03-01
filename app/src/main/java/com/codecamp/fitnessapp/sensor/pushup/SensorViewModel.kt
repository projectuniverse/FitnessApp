package com.codecamp.fitnessapp.sensor.pushup

import androidx.lifecycle.ViewModel
import com.codecamp.fitnessapp.sensor.pushup.PushUpUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val insideWorkoutRepository: PushUpRepository,
    val pushupUtil: PushUpUtil
): ViewModel() {
    val proximitySensorData = insideWorkoutRepository.proximitySensorData

    //val test = MutableStateFlow(0)

    //val repetitions = pushupUtil.repetitions

    //init {
        //pushupUtil.x = proximitySensorData
        /*viewModelScope.launch {
            while(true) {
                test.value++
                delay(1000)
            }
        }*/
    //}

    fun updateRepetitions(): Int {
        return pushupUtil.checkPushUp(proximitySensorData.value)
    }

    fun start() {
        insideWorkoutRepository.startListening()

    }

    fun stop() {
        insideWorkoutRepository.stopListening()
    }
}