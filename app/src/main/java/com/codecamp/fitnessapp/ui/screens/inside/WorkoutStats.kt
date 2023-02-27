package com.codecamp.fitnessapp.ui.screens.inside

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.R

@Composable
fun WorkoutStats(time: String, repetitions: Int, kCal: Int) {

    val workoutStats = stringArrayResource(R.array.WorkoutStats)

    Spacer(modifier = Modifier.height(40.dp))

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = repetitions.toString(), fontSize = 140.sp)
        Text(text = workoutStats[2], fontSize = 25.sp)
    }

    Spacer(modifier = Modifier.height(40.dp))

    Row(
        modifier = Modifier.fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = time, fontSize = 35.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = workoutStats[0])
        }

        Spacer(modifier = Modifier.width(40.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = kCal.toString(), fontSize = 35.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = workoutStats[5])
        }
    }
}
