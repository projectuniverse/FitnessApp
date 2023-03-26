package com.codecamp.fitnessapp.ui.screens.result

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.OutsideWorkout

@Composable
fun OutsideResult(
    outsideWorkout: OutsideWorkout,
    resultViewModel: ResultViewModel = hiltViewModel()
) {
    val workoutStats = stringArrayResource(R.array.WorkoutStats)

    val tracklist = resultViewModel.tracklist.collectAsState(initial = mutableListOf()).value
    val usedTracks = resultViewModel.getUsedTracks(tracklist, outsideWorkout.id)
    val latlngList = resultViewModel.getLatlngList(usedTracks)
    val altitudeList = resultViewModel.getAltitudeList(usedTracks)

    /*
    * All information from the workout is displayed.
    * For every trivial information a custom result card is used.
    * */
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

                ResultCard(name = workoutStats[1], value = outsideWorkout.distance.toString() + "km")

                Spacer(modifier = Modifier.height(10.dp))

                ResultCard(name = workoutStats[0], value = resultViewModel.getElapsedTime(outsideWorkout.endTime, outsideWorkout.startTime))

                Spacer(modifier = Modifier.height(10.dp))

                ResultCard(name = workoutStats[3], value = resultViewModel.formatTime(outsideWorkout.pace) + "min/km")

                if(outsideWorkout.name != stringArrayResource(R.array.outsideActivities)[2]) {
                    Spacer(modifier = Modifier.height(10.dp))

                    ResultCard(name = workoutStats[6], value = outsideWorkout.steps.toString())
                }

                Spacer(modifier = Modifier.height(10.dp))

                ResultCard(name = workoutStats[5], value = outsideWorkout.kcal.toString())

                Spacer(modifier = Modifier.height(10.dp))

                /*
                * A graph that shows the altitude changes
                * */
                AltitudeResult(altitudeList)

                Spacer(modifier = Modifier.height(10.dp))

                /*
                * A map that shows the route the user travelled
                * */
                MapResult(latlngList)

            }
        }
    }
}