package com.codecamp.fitnessapp.ui.viewmodel.insideutil

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
    private val state = mutableStateOf(PushUpState.START)
    val repetitions = MutableStateFlow(0)

    fun checkPushUp(sensorValues: List<Float>): Int {
        var  rotX = sensorValues[0]

        if (abs(rotX) < 0.5f) {
            rotX = 0f
        }

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