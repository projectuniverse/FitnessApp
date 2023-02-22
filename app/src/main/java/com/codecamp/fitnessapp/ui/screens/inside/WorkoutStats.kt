package com.codecamp.fitnessapp.ui.screens.inside

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.R

@Composable
fun WorkoutStats(time: String, repetitions: Int, kCal: Int, onClick: () -> Unit, ) {

    val workoutStats = stringArrayResource(R.array.WorkoutStats)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(0.9f)) {
            IconButton(
                onClick = { onClick() }
            ) {
                Icon(
                    Icons.Default.ArrowCircleLeft,
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Column() {
            Text(text = repetitions.toString(), fontSize = 80.sp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = workoutStats[2])
        }

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(text = time)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = workoutStats[0])
            }

            Spacer(modifier = Modifier.width(40.dp))

            Column() {
                Text(text = kCal.toString())
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = workoutStats[5])
            }
        }
    }
}