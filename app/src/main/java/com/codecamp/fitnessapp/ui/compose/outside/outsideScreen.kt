package com.codecamp.fitnessapp.ui.compose.outside

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codecamp.fitnessapp.ui.compose.selectworkout.map
import com.codecamp.fitnessapp.ui.theme.FitnessAppTheme
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


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