package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout
import com.codecamp.fitnessapp.ui.viewmodel.WorkoutViewModel

@Composable
fun LastWorkouts(
    showOldInside: (insideWorkout: InsideWorkout) -> Unit,
    showOldOutside: (outsideWorkout: OutsideWorkout) -> Unit,
    workoutViewModel: WorkoutViewModel = hiltViewModel()
) {
    val oldOutsides = workoutViewModel.oldOutsideWorkouts.collectAsState(initial = mutableListOf()).value
    val oldInsides = workoutViewModel.oldInsideWorkouts.collectAsState(initial = mutableListOf()).value

    val indexSequence: List<Int> = workoutViewModel.getSortedSequenz(oldInsides, oldOutsides)

    if (oldOutsides.isNotEmpty() || oldInsides.isNotEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (endTime in indexSequence) {
                for (inside in oldInsides) {
                    if (inside.endTime == endTime) OldInsideWorkout(inside, showOldInside)
                }
                for (outside in oldOutsides) {
                    if (outside.endTime == endTime) OldOutsideWorkout(outside, showOldOutside)
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    } else {
        NoWorkoutsWarning()
    }
}
