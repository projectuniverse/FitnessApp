package com.codecamp.fitnessapp.ui.screens.outside

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codecamp.fitnessapp.ui.compose.selectworkout.map
import com.codecamp.fitnessapp.ui.theme.FitnessAppTheme


@Composable
fun outsideScreen(outsideWorkoutType: String) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = outsideWorkoutType)
        backButton(name = "back", {})

        map()

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FitnessAppTheme {

    }
}