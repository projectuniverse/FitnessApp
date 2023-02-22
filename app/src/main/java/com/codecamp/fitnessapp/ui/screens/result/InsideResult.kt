package com.codecamp.fitnessapp.ui.screens.result

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.InsideWorkout
// import com.codecamp.fitnessapp.model.UnitConverter

@Composable
fun InsideResult(
    insideWorkout: InsideWorkout, backToDashboard: () -> Unit
) {
    //val unitConverter = UnitConverter()
    val workoutStats = stringArrayResource(R.array.WorkoutStats)
    val time = "10" //unitConverter.millisecondsToTimer((insideWorkout.endTime-insideWorkout.startTime).toLong())

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

        Text(text = insideWorkout.name, fontSize = 42.sp)

        ResultCard(name = workoutStats[2], value = insideWorkout.repetitions.toString())

        Spacer(modifier = Modifier.height(4.dp))

        ResultCard(name = workoutStats[0], value = time)

        Spacer(modifier = Modifier.height(4.dp))

        ResultCard(name = workoutStats[5], value = insideWorkout.kcal.toString())

    }
}