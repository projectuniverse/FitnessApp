package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.OutsideWorkout

@Composable
fun OldOutsideWorkout(
    outsideWorkout: OutsideWorkout,
    showOldWorkout: (outsideWorkout: OutsideWorkout) -> Unit
) {

    val workoutNames = stringArrayResource(R.array.outsideActivities)
    val workoutInfos = stringArrayResource(R.array.oldWorkout)

    Card(
        modifier = Modifier.fillMaxWidth(0.8f).height(80.dp).clickable { showOldWorkout(outsideWorkout) },
        border = BorderStroke(4.dp, Color.Blue),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = workoutInfos[0] + outsideWorkout.distance.toString() + workoutInfos[3],
                    fontSize = 25.sp
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = workoutInfos[2] + outsideWorkout.endTime + workoutInfos[5],
                    fontSize = 25.sp
                )
            }
        }
    }
}