package com.codecamp.fitnessapp.sensor.pushup

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.abs


enum class PushUpState {
    START,
    DOWN,
    PAUSE,
    UP
}
class PushUpUtil {
    var state = mutableStateOf(PushUpState.START)
    val repetitions = MutableStateFlow(0)

    fun checkPushUp(sensorValues: List<Float>): Int {
        var  rotX = sensorValues[0]
        //var rotY = sensorValues[1]
        //var rotZ = sensorValues[2]

        if (abs(rotX) < 0.5f) {
            rotX = 0f
        }

        //Log.d("SENSOSR_DATA_X", rotX.toString() + "||State: " + state.value.name)

        when(state.value)  {
            PushUpState.START -> {
                if (rotX > 0) {
                    state.value = PushUpState.DOWN
                }
            }
            PushUpState.DOWN -> {
                if (rotX == 0f) {
                    state.value = PushUpState.PAUSE
                }
            }
            PushUpState.PAUSE -> {
                if (rotX < 0f) {
                    state.value = PushUpState.UP
                }
            }
            PushUpState.UP -> {
                if (rotX == 0f) {
                    state.value = PushUpState.START
                    repetitions.value++
                }
            }
        }

        return repetitions.value
    }
}