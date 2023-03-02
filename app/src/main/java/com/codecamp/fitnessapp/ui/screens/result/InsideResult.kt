package com.codecamp.fitnessapp.ui.screens.result

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.InsideWorkout

@Composable
fun InsideResult(
    insideWorkout: InsideWorkout
) {
    //val unitConverter = UnitConverter()
    val workoutStats = stringArrayResource(R.array.WorkoutStats)
    val time = "10" //unitConverter.millisecondsToTimer((insideWorkout.endTime-insideWorkout.startTime).toLong())

    Column(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        ResultCard(name = workoutStats[2], value = insideWorkout.repetitions.toString())

        Spacer(modifier = Modifier.height(10.dp))

        ResultCard(name = workoutStats[0], value = time)

        Spacer(modifier = Modifier.height(10.dp))

        ResultCard(name = workoutStats[5], value = insideWorkout.kcal.toString())

    }
}