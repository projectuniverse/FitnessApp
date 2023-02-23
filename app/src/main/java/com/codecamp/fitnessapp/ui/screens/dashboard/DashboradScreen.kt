package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    showOldInside: (insideWorkout: InsideWorkout) -> Unit,
    showOldOutside: (outsideWorkout: OutsideWorkout) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            items(count = 1) {
//                TopLabel(showSettings)
//                Spacer(modifier = Modifier.height(60.dp))
                WeatherScreen()
                Spacer(modifier = Modifier.height(60.dp))
                LastWorkouts(showOldInside, showOldOutside)
            }
        }
    }
}
