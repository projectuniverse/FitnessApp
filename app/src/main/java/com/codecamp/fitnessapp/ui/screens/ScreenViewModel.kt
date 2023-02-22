package com.codecamp.fitnessapp.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecamp.fitnessapp.data.weather.DefaultWeatherRepository
import com.codecamp.fitnessapp.data.weather.WeatherRepository
import com.codecamp.fitnessapp.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScreenViewModel
@Inject constructor(
    private val weatherRepository: DefaultWeatherRepository,
) : ViewModel() {
    val weather = weatherRepository.weather
    init {

        viewModelScope.launch {
            weatherRepository.fetchWeather(51.31,9.48)
        }
    }
}