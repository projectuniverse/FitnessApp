package com.codecamp.fitnessapp.ui.screens.outside

import android.location.Location
import android.util.Log
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
import com.codecamp.fitnessapp.model.User
import com.google.android.gms.maps.model.LatLng


@Composable
fun OutsideScreen(
    workoutName: String,
    stopWorkout: (newOutside: OutsideWorkout) -> Unit,
    workoutViewModel: OutsideViewModel = hiltViewModel()
) {
    val workoutStats = stringArrayResource(R.array.WorkoutStats)
    val timerDefaultText = stringResource(R.string.timer)
    val distanceDefaultText = stringResource(R.string.distance)
    val paceDefaultText = stringResource(R.string.pace)
    val paceKmDefaultText = stringResource(R.string.paceKm)
    val currentUser = workoutViewModel.user.collectAsState(initial = null)

    val loc = Location("")
    loc.latitude = 51.3204621
    loc.longitude = 9.4886897
    val location = workoutViewModel.locationFlow.collectAsState(initial = loc)
    Log.d("Test2", "${loc.latitude} ${loc.longitude}")

    var workoutState by remember { mutableStateOf("ready") }
    var buttontext by remember { mutableStateOf(workoutStats[7]) }
    var time by remember { mutableStateOf(timerDefaultText) }
    var distance by remember { mutableStateOf(distanceDefaultText) }
    var pace by remember { mutableStateOf(paceDefaultText) }
    var paceKm by remember { mutableStateOf(paceKmDefaultText) }

    val latlngList = workoutViewModel.getLatLngList()

    workoutViewModel.timePassed.observe(LocalLifecycleOwner.current) {
        time = it
        workoutViewModel.updateTracks()
    }

    workoutViewModel.distance.observe(LocalLifecycleOwner.current) { distance = it }
    workoutViewModel.pace.observe(LocalLifecycleOwner.current) { pace = it }
    workoutViewModel.paceKm.observe(LocalLifecycleOwner.current) { paceKm = it }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = workoutName, fontSize = 40.sp)

        WorkoutStats(time = time, distance = distance, paceKm = paceKm, pace = pace)

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .weight(1f, true),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Map(latlngList, LatLng(location.value.latitude, location.value.longitude))
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    if (currentUser.value != null) {
                        if (workoutState == "ready") {
                            workoutViewModel.switchWorkingOut()
                            buttontext = workoutStats[8]
                            workoutState = "active"
                            workoutViewModel.createNewTrack()
                        } else if (workoutState == "active") {
                            workoutViewModel.switchWorkingOut()
                            workoutState = "stopped"
                            buttontext = workoutStats[9]
                        } else if (workoutState == "stopped") {
                            val result = workoutViewModel.createOutsideWorkout(
                                workoutName,
                                currentUser.value!!
                            )
                            workoutViewModel.saveOutsideWorkout(result)
                            stopWorkout(result)
                        }
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