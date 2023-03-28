package com.codecamp.fitnessapp.ui.screens.inside.insideutil

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.abs

enum class SitUpState {
    START,
    UP,
    PAUSE,
    DOWN
}

class SitUpUtil {
    private val state = mutableStateOf(SitUpState.START)
    var repetitions = MutableStateFlow(0)

    fun checkRepetition(sensorValues: List<Float>): Int {
        var rotY = sensorValues[1]

        if (abs(rotY) < 1f) {
            rotY = 0f
        }

        when(state.value)  {
            SitUpState.START -> {
                if (rotY > 0) {
                    state.value = SitUpState.UP
                }
            }
            SitUpState.UP -> {
                if (rotY == 0f) {
                    state.value = SitUpState.PAUSE
                }
            }
            SitUpState.PAUSE -> {
                if (rotY < 0f) {
                    state.value = SitUpState.DOWN
                }
            }
            SitUpState.DOWN -> {
                if (rotY == 0f) {
                    state.value = SitUpState.START
                    repetitions.value++
                }
            }
        }

        return repetitions.value
    }
}