package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.OutsideWorkout
import com.codecamp.fitnessapp.ui.viewmodel.WorkoutViewModel

@Composable
fun OldOutsideWorkout(
    outsideWorkout: OutsideWorkout,
    showOldWorkout: (outsideWorkout: OutsideWorkout) -> Unit,
    workoutViewModel: WorkoutViewModel = hiltViewModel()
) {

    val workoutNames = stringArrayResource(R.array.outsideActivities)
    val workoutInfos = stringArrayResource(R.array.oldWorkout)

    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(80.dp)
            .clickable { showOldWorkout(outsideWorkout) },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.width(64.dp),
                verticalArrangement = Arrangement.Center
            ) {
                val icon = when (outsideWorkout.name) {
                    workoutNames[0] -> Icons.Default.DirectionsRun
                    workoutNames[1] -> Icons.Default.Hiking
                    else -> {
                        Icons.Default.PedalBike
                    }
                }

                Icon(icon, contentDescription = "", modifier = Modifier.size(64.dp))
            }

            val workoutDetails = workoutInfos[0] + " " + outsideWorkout.distance.toString() + workoutInfos[3] + "\n" +
                    workoutInfos[2] + " " + workoutViewModel.getElapsedTime(outsideWorkout.endTime, outsideWorkout.startTime)

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = workoutDetails,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 25.sp
                )
            }
        }
    }
}