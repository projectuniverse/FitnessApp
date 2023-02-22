package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.NearMe
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.R
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
