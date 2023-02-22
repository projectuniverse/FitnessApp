package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout

@Composable
fun LastWorkouts(
    showOldInside: (insideWorkout: InsideWorkout) -> Unit,
    showOldOutside: (outsideWorkout: OutsideWorkout) -> Unit
) {
    //TODO viewmodel.getOldWorkouts oder halt mit repo
    val oldWorkouts = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ,13)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (workout in oldWorkouts) {
            //TODO eigentlich sowas wie OldWorkout(workout) EZ wo Problem
            //OldInsideWorkout(workout, showOldWorkout)
            Text(text = "TEST", fontSize = 40.sp)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}