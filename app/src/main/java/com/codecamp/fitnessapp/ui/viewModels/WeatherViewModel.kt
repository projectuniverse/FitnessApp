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
import com.codecamp.fitnessapp.model.Weather

sealed interface WeatherUiState {
    data class Success(val weather: Weather) : WeatherUiState
    data class Error(val weather: Weather?) : WeatherUiState
    object Loading : WeatherUiState
}

class WeatherViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    var weatherUiState: WeatherUiState by mutableStateOf(WeatherUiState.Loading)
        private set

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FitnessApplication)
                val weatherRepository = application.container.weatherRepository
                WeatherViewModel(
                    weatherRepository = weatherRepository
                )
            }
        }
    }
}