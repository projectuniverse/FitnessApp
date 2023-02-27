package com.codecamp.fitnessapp.ui.screens.dashboard.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WeatherForecast(forecast: String) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.width(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if(!(forecast == "Clear" || forecast == "Clouds")) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = "",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "Forecast: $forecast")
    }
}