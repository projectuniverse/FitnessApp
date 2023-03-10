package com.codecamp.fitnessapp.sensor.situp

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
    val state = mutableStateOf(SitUpState.START)
    var repetitions = MutableStateFlow(0)
    var counter = 0f
    val threshold = 15

    fun checkRepetition(sensorValues: List<Float>): Int {
        var  rotX = sensorValues[0]
        var rotY = sensorValues[1]
        var rotZ = sensorValues[2]

/*        if (abs(rotY) < 1f) {
            rotY = 0f
        }*/

        counter += rotY

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