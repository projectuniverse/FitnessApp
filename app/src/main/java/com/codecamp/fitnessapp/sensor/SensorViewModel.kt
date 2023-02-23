package com.codecamp.fitnessapp.sensor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SensorViewModel @Inject constructor(
    private val insideWorkoutRepository: InsideWorkoutRepositoryInterface,
    private val pushupUtil: PushupUtil
): ViewModel() {
    val proximitySensorData = insideWorkoutRepository.proximitySensorData

    val repetitions = pushupUtil.repetitions

    fun cool() {
    }

    fun start() {
        insideWorkoutRepository.startListening()
    }

    fun stop() {
        insideWorkoutRepository.stopListening()
    }
}