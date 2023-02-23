package com.codecamp.fitnessapp.sensor

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow


enum class PushupState {
    START,
    DOWN
}
class PushupUtil {
    var state = mutableStateOf(PushupState.START)
    val repetitions = MutableStateFlow(0)
    private val threshold = 1.0f
    var x = MutableStateFlow(0.0f)

    fun name() {
        repetitions.value++
        /*when(state.value) {
            PushupState.START -> {
                //check if sensor data falls under threshold
                // yes -> change state to DOWN
                if (x.value < threshold) {
                    state.value = PushupState.DOWN
                }
            }
            PushupState.DOWN -> {
                //check if sensor data falls above threshold
                // yes -> change state to START
                //        increase repetitions
                if (x.value >= threshold) {
                    state.value = PushupState.START
                    repetitions.value++
                }
            }
        }*/
    }
}