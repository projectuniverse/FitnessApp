package com.codecamp.fitnessapp.ui.screens.inside

// import com.codecamp.fitnessapp.model.UnitConverter
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.User

@Composable
fun InsideScreen(
    newInside: InsideWorkout,
    stopWorkout: (newInside: InsideWorkout) -> Unit,
    navigateBack: () -> Boolean,
    startTime: Long,
    //workoutViewmodel: WorkoutViewModel = viewModel(factory = WeatherViewModel.Factory),
    //userViewmodel: UserViewModel = viewModel(factory = WeatherViewModel.Factory)
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

    //workoutViewmodel.timePassed.observe(LocalLifecycleOwner.current) {
    //    time = unitConverter.millisecondsToTimer(it - startTime)
    //}

    //workoutViewmodel.repetitions.observe(LocalLifecycleOwner.current) {
    //    repetitions++
    //    kCal = unitConverter.calculateKCal(newInside.name, repetitions, currentUser)
    //}

    WorkoutStats(time, repetitions, kCal) {
        time = timerDefaultText
        kCal = 0
        repetitions = 0
        navigateBack()
    }

    BottomAppBar {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if(workoutActive) {
                        stopWorkout(newInside)
                        //workoutViewmodel.addInsideWorkout(newInside)
                    } else {
                        buttontext = workoutStats[8]
                        //workoutViewmodel.startTimer(newInside.name)
                        workoutActive = true
                    }

                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(bottom = 50.dp)
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