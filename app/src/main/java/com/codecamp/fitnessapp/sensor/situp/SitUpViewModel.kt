package com.codecamp.fitnessapp.sensor.situp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SitUpViewModel @Inject constructor(
    private val sitUpRepository: SitUpRepository,
    private val sitUpUtil: SitUpUtil
): ViewModel() {
}