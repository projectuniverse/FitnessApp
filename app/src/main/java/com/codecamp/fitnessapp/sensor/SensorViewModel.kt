package com.codecamp.fitnessapp.sensor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val insideWorkoutRepository: InsideWorkoutRepositoryInterface,
    val pushupUtil: PushupUtil
): ViewModel() {
    val proximitySensorData = insideWorkoutRepository.proximitySensorData

    //val test = MutableStateFlow(0)

    val repetitions = pushupUtil.repetitions

    init {
        pushupUtil.x = proximitySensorData
        /*viewModelScope.launch {
            while(true) {
                test.value++
                delay(1000)
            }
        }*/
    }

    fun start() {
        insideWorkoutRepository.startListening()

    }

    fun stop() {
        insideWorkoutRepository.stopListening()
    }
}