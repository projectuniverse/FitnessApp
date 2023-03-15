package com.codecamp.fitnessapp.ui.screens.outside

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.OutsideWorkout
import com.codecamp.fitnessapp.ui.viewmodel.WorkoutViewModel


@Composable
fun OutsideScreen(
    workoutName: String,
    stopWorkout: (newOutside: OutsideWorkout) -> Unit,
    workoutViewModel: WorkoutViewModel = hiltViewModel()
) {
    val workoutStats = stringArrayResource(R.array.WorkoutStats)
    val timerDefaultText = stringResource(R.string.timer)

    var workoutActive by remember { mutableStateOf(false) }
    var buttontext by remember { mutableStateOf(workoutStats[7]) }
    var time by remember { mutableStateOf(timerDefaultText) }

    workoutViewModel.timePassed.observe(LocalLifecycleOwner.current) {
        time = it
        workoutViewModel.updateRepetitions(workoutName)
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = workoutName, fontSize = 40.sp)

        WorkoutStats(time = time, distance = "0.00", paceKm = "00:00", pace = "00:00")

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .weight(1f, true),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Map()
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if(workoutActive) {
                        val result = OutsideWorkout(0, workoutName, 0.0, 0, 0.0, 0, 0, 0)
                        stopWorkout(result)
                        workoutViewModel.switchWorkingOut()
                    } else {
                        buttontext = workoutStats[8]
                        workoutViewModel.switchWorkingOut()
                        workoutActive = true
                    }

                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(3.dp)
            ) {
                Text(
                    text = buttontext,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Filled.ArrowForward,"")
            }
        }


    }
}