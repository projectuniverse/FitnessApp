package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout
import com.codecamp.fitnessapp.ui.screens.dashboard.weather.WeatherLabel

/*
* In the Dashboardscreen the weather and last workouts are displayed
* */
@Composable
fun DashboardScreen(
    showOldInside: (insideWorkout: InsideWorkout) -> Unit,
    showOldOutside: (outsideWorkout: OutsideWorkout) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            items(count = 1) {
                Spacer(modifier = Modifier.height(40.dp))
                WeatherLabel()
                Spacer(modifier = Modifier.height(60.dp))
                LastWorkouts(showOldInside, showOldOutside)
            }
        }
    }
}
