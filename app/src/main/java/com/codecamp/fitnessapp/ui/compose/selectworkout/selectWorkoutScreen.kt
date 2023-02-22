package com.codecamp.fitnessapp.ui.compose.selectworkout

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codecamp.fitnessapp.ui.theme.FitnessAppTheme

@Composable
fun selectWorkoutScreen() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Outside")
        workoutButton(name = "Bike")
        workoutButton(name = "Walk")
        workoutButton(name = "Run")

        Text(text = "Inside")
        workoutButton(name = "Squats")
        workoutButton(name = "Pushups")
        workoutButton(name = "Situps")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FitnessAppTheme {
        selectWorkoutScreen()
    }
}