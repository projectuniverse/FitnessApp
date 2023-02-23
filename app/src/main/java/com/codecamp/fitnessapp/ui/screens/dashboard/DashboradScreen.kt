package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout

@Composable
fun DashboardScreen(
    showSettings: () -> Unit,
    showOldInside: (insideWorkout: InsideWorkout) -> Unit,
    showOldOutside: (outsideWorkout: OutsideWorkout) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            items(count = 1) {
                TopLabel(showSettings)
                Spacer(modifier = Modifier.height(60.dp))
                WeatherScreen()
                Spacer(modifier = Modifier.height(60.dp))
                LastWorkouts(showOldInside, showOldOutside)
            }
        }
    }
}
