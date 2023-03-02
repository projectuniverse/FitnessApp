package com.codecamp.fitnessapp.ui.screens.inside

import androidx.compose.foundation.layout.*
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
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.User
import com.codecamp.fitnessapp.ui.viewmodel.WorkoutViewModel

@Composable
fun InsideScreen(
    newInside: InsideWorkout,
    stopWorkout: (newInside: InsideWorkout) -> Unit,
    workoutViewModel: WorkoutViewModel = hiltViewModel()
) {
    // val unitConverter = UnitConverter()
    val workoutStats = stringArrayResource(R.array.WorkoutStats)
    val timerDefaultText = stringResource(R.string.timer)
    val currentUser = User(0, 18, 180, 80)

    var workoutActive by remember { mutableStateOf(false) }
    var buttontext by remember { mutableStateOf(workoutStats[7]) }
    var time by remember { mutableStateOf(timerDefaultText) }
    var kCal by remember { mutableStateOf(0) }
    var repetitions by remember { mutableStateOf(0) }


    workoutViewModel.timePassed.observe(LocalLifecycleOwner.current) {
        time = it
    }

    workoutViewModel.repetitions.observe(LocalLifecycleOwner.current) {
        repetitions = it
        kCal = workoutViewModel.calculateKCalInside(newInside.name, repetitions, currentUser)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        WorkoutStats(time, repetitions, kCal)
        
        Spacer(modifier = Modifier.height(100.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, false)
        ) {
            Button(
                onClick = {
                    if(workoutActive) {
                        stopWorkout(newInside)
                        workoutViewModel.switchWorkingOut()
                    } else {
                        buttontext = workoutStats[8]
                        workoutViewModel.switchWorkingOut()
                        workoutActive = true
                    }

                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary)
            ) {
                Text(
                    text = buttontext,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Filled.ArrowForward,"")
            }
        }
    }

}