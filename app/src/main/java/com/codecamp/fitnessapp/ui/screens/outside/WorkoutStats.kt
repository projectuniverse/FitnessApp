package com.codecamp.fitnessapp.ui.screens.outside

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
fun WorkoutStats(time: String, distance: String, paceKm: String, pace: String) {

    val workoutStats = stringArrayResource(R.array.WorkoutStats)

    Column(
        modifier = Modifier.fillMaxWidth(0.9f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = time, fontSize = 35.sp)
        Text(text = workoutStats[0])
    }

    Row(
        modifier = Modifier.fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.width(100.dp)
        ) {
            Text(text = distance, fontSize = 20.sp)
            Text(text = "Distance (km)", fontSize = 12.sp)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.width(100.dp)
        ) {
            Text(text = paceKm, fontSize = 20.sp)
            Text(text = "last km (min)", fontSize = 12.sp)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.width(100.dp)
        ) {
            Text(text = pace, fontSize = 20.sp)
            Text(text = "Ø (min/km)", fontSize = 12.sp)
        }
    }
}
