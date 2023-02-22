package com.codecamp.fitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.codecamp.fitnessapp.data.weather.DefaultWeatherRepository
import com.codecamp.fitnessapp.data.weather.WeatherRepository
import com.codecamp.fitnessapp.network.WeatherApiService
import com.codecamp.fitnessapp.ui.FitnessApp
import com.codecamp.fitnessapp.ui.theme.FitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println()

        setContent {
            FitnessAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    FitnessApp()
                }
            }
        }
    }
}