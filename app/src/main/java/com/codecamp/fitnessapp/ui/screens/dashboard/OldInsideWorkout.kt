package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.InsideWorkout


/*
* By clicking on a card you get to the resultscreen of the chosen workout
* */
@Composable
fun OldInsideWorkout(
    insideWorkout: InsideWorkout,
    showOldWorkout: (insideWorkout: InsideWorkout) -> Unit,
    workoutViewModel: DashboardViewModel = hiltViewModel()
) {

    val workoutNames = stringArrayResource(R.array.insideActivities)
    val workoutInfos = stringArrayResource(R.array.oldWorkout)

    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(80.dp)
            .clickable { showOldWorkout(insideWorkout) },
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
                val icon = when(insideWorkout.name) {
                    workoutNames[0] -> painterResource(R.drawable.squats)
                    workoutNames[1] -> painterResource(R.drawable.pushups)
                    else -> painterResource(R.drawable.situps)
                }

                Icon(icon, contentDescription = "", modifier = Modifier.size(64.dp))
            }

            val workoutDetails = workoutInfos[1] + " " + insideWorkout.repetitions + "\n" +
                    workoutInfos[2] + " " + workoutViewModel.getElapsedTime(insideWorkout.endTime, insideWorkout.startTime)

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