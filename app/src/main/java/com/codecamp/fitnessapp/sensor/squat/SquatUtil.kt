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
    var state = mutableStateOf(SquatState.START)
    val repetitions = MutableStateFlow(0)
    private val threshold = 10
    var counter = 0f

    fun checkSquat(sensorValues: List<Float>): Int {
        val x = sensorValues[0]
        var y = sensorValues[1]
        val z = sensorValues[2]

        if (abs(y) < 1) {
            y = 0f
        }

        /*if ((state.value == SquatState.DOWN || state.value == SquatState.UP) && y > 0.3f) {
            counter += y
        }*/

        when(state.value) {
            SquatState.START -> {
                if(y > 0.3f) {
                    state.value = SquatState.DOWN
                }
            }
            SquatState.DOWN -> {
                if (y < 0f) {
                    counter += abs(y)
                }
                if (y <= 0.0f && counter >= threshold) {
                    state.value = SquatState.PAUSE
                    counter = 0f
                }
            }
            SquatState.PAUSE -> {
                if (y > 0.3f) {
                    state.value = SquatState.UP
                }
            }
            SquatState.UP -> {
                if (y > 0f) {
                    counter += y
                }

                if (y <= 0.0f && counter >= threshold) {
                    state.value = SquatState.START
                    repetitions.value++
                    counter = 0f
                }
            }
        }


        return repetitions.value
    }
}