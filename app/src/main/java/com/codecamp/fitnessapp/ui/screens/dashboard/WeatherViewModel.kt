package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecamp.fitnessapp.data.weather.DefaultWeatherRepository
import com.codecamp.fitnessapp.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject constructor(
    private val weatherRepository: DefaultWeatherRepository,
) : ViewModel() {
    val weather = weatherRepository.weather
    init {
        viewModelScope.launch {
            weatherRepository.fetchWeather(51.31,9.48)
        }
    }

    /*
     * Only call with weather != null
     */
    fun weatherUpToDate(weather: Weather): Boolean {
        val currentTime = System.currentTimeMillis() / 1000
        val difference = (currentTime - weather.time) / 60.0
        return difference <= 60.0
    }
}