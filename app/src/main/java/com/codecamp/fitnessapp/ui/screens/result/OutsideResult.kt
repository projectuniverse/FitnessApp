package com.codecamp.fitnessapp.ui.screens.result

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.OutsideWorkout

@Composable
fun OutsideResult(outsideWorkout: OutsideWorkout) {
    val workoutStats = stringArrayResource(R.array.WorkoutStats)
    val time = stringResource(R.string.timer) //unitConverter.millisecondsToTimer((insideWorkout.endTime-insideWorkout.startTime).toLong())

    Column(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            items(count = 1) {
                Spacer(modifier = Modifier.height(20.dp))

                ResultCard(name = workoutStats[1], value = outsideWorkout.distance.toString())

                Spacer(modifier = Modifier.height(10.dp))

                ResultCard(name = workoutStats[0], value = time)

                Spacer(modifier = Modifier.height(10.dp))

                ResultCard(name = workoutStats[3], value = outsideWorkout.pace.toString())

                Spacer(modifier = Modifier.height(10.dp))

                AltitudeResult()

                if(outsideWorkout.name != stringArrayResource(R.array.outsideActivities)[2]) {
                    Spacer(modifier = Modifier.height(10.dp))

                    ResultCard(name = workoutStats[6], value = outsideWorkout.steps.toString())
                }

                Spacer(modifier = Modifier.height(10.dp))

                ResultCard(name = workoutStats[5], value = outsideWorkout.kcal.toString())

                Spacer(modifier = Modifier.height(10.dp))

                //TODO KARTE

            }
        }
    }
}