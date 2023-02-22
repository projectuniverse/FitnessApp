package com.codecamp.fitnessapp.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.codecamp.fitnessapp.FitnessApplication
import com.codecamp.fitnessapp.data.weather.WeatherRepository
import com.codecamp.fitnessapp.data.workout.WorkoutRepository

class WorkoutViewModel(
    private val workoutRepository: WorkoutRepository
) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FitnessApplication)
                val workoutRepository = application.container.workoutRepository
                WorkoutViewModel(
                    workoutRepository = workoutRepository
                )
            }
        }
    }
}