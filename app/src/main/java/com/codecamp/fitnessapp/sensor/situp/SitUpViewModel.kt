package com.codecamp.fitnessapp.sensor.situp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SitUpViewModel @Inject constructor(
    private val sitUpRepository: SitUpRepository,
    val sitUpUtil: SitUpUtil
): ViewModel() {
    val gyroscopeData = sitUpRepository.gyroscopeData

    fun checkSitUp() {
        sitUpUtil.checkRepetition(gyroscopeData.value)
    }

    fun startListening() {
        sitUpRepository.startListening()
    }

    fun stopListening() {
        sitUpRepository.stopListening()
    }
}