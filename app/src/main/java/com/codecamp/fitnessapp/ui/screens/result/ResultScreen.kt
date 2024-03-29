package com.codecamp.fitnessapp.ui.screens.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout

@Composable
fun ResultScreen(
    insideWorkout: InsideWorkout?,
    outsideWorkout: OutsideWorkout?,
    new: Boolean
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        /*
        * In dependence of which workout is done, the outside or inside workout are displayed
        * */
        if (outsideWorkout != null) {
            OutsideResult(outsideWorkout,new = new)
        } else if (insideWorkout != null) {
            InsideResult(insideWorkout)
        }
    }
}