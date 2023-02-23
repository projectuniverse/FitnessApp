package com.codecamp.fitnessapp.ui.screens.result

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.OutsideWorkout

@Composable
fun OutsideResult(outsideWorkout: OutsideWorkout, backToDashboard: () -> Unit) {
    val workoutStats = stringArrayResource(R.array.WorkoutStats)
    val time = stringResource(R.string.timer) //unitConverter.millisecondsToTimer((insideWorkout.endTime-insideWorkout.startTime).toLong())

    Column(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth(0.9f)) {
            IconButton(
                onClick = { backToDashboard() }
            ) {
                Icon(
                    Icons.Default.ArrowCircleLeft,
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = outsideWorkout.name, fontSize = 42.sp)

        ResultCard(name = workoutStats[1], value = outsideWorkout.distance.toString())

        Spacer(modifier = Modifier.height(4.dp))

        ResultCard(name = workoutStats[0], value = time)

        Spacer(modifier = Modifier.height(4.dp))

        ResultCard(name = workoutStats[3], value = outsideWorkout.pace.toString())

        //TODO GRAPH

        if(outsideWorkout.name != stringArrayResource(R.array.outsideActivities)[2]) {
            Spacer(modifier = Modifier.height(4.dp))

            ResultCard(name = workoutStats[6], value = outsideWorkout.steps.toString())
        }

        Spacer(modifier = Modifier.height(4.dp))

        ResultCard(name = workoutStats[5], value = outsideWorkout.kcal.toString())

        Spacer(modifier = Modifier.height(4.dp))

        //TODO KARTE

    }
}