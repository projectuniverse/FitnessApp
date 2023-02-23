package com.codecamp.fitnessapp.ui.screens.inside

// import com.codecamp.fitnessapp.model.UnitConverter
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.User
import com.codecamp.fitnessapp.ui.FitnessApp

@Composable
fun InsideScreen(
    newInside: InsideWorkout,
    stopWorkout: (newInside: InsideWorkout) -> Unit,
    navigateBack: () -> Unit
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
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier.width(100.dp),
                onClick = {
                    time = timerDefaultText
                    kCal = 0
                    repetitions = 0
                    navigateBack()
                }
            ) {
                Icon(
                    Icons.Default.ArrowCircleLeft,
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)
                )
            }
            Text(text = newInside.name, fontSize = 60.sp)
        }

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
                        //workoutViewmodel.addInsideWorkout(newInside)
                    } else {
                        buttontext = workoutStats[8]
                        //workoutViewmodel.startTimer(newInside.name)
                        workoutActive = true
                    }

                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(bottom = 30.dp)
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

@Preview
@Composable
fun Default() {
    MaterialTheme() {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
            InsideScreen(newInside = InsideWorkout(0, "",0,0,0,0), stopWorkout = {}) {

            }
        }
    }
}