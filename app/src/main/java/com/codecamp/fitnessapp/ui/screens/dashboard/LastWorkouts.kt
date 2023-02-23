package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.layout.*
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
//    val oldInsides = mutableListOf(
//        InsideWorkout(0, "Squats",34,0,3,500),
//        InsideWorkout(0, "Pushups",576,0,4, 900),
//        InsideWorkout(0, "Squats",890,0,9,1400),
//        InsideWorkout(0, "Situps",567,0,12,999),
//        InsideWorkout(0, "Squats",90,0,56,314159),
//    )

//    val oldOutsides = mutableListOf(
//        OutsideWorkout(0,"Biking",12.7,3000,60.4, 0, 1, 0),
//        OutsideWorkout(0,"Biking",40.5,4000,12.5, 0, 2, 0),
//        OutsideWorkout(0,"Running",13.5,400,324.6, 0, 14, 0),
//        OutsideWorkout(0,"Running",40.5,424,34.7, 0, 30, 0),
//        OutsideWorkout(0,"Hiking",5.7,0,92.7, 0, 60, 0)
//    )

    val indexSequence: List<Int> = workoutViewModel.getSortedSequenz(oldInsides, oldOutsides)

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
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}
