package com.codecamp.fitnessapp.sensor.squat

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.abs

enum class SquatState {
    START,
    DOWN,
    PAUSE,
    UP
}

class SquatUtil {
    private val state = mutableStateOf(SquatState.START)
    val repetitions = MutableStateFlow(0)

    fun checkRepetition(sensorValues: List<Float>): Int {
        var  rotX = sensorValues[0]

        if (abs(rotX) < 0.5f) {
            rotX = 0f
        }

        when(state.value)  {
            SquatState.START -> {
                if (rotX < 0) {
                    state.value = SquatState.DOWN
                }
            }
            SquatState.DOWN -> {
                if (rotX == 0f) {
                    state.value = SquatState.PAUSE
                }
            }
            SquatState.PAUSE -> {
                if (rotX > 0f) {
                    state.value = SquatState.UP
                }
            }
            SquatState.UP -> {
                if (rotX == 0f) {
                    state.value = SquatState.START
                    repetitions.value++
                }
            }
        }

        return repetitions.value
    }
}