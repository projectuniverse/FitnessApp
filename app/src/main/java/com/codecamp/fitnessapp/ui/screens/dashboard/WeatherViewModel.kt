package com.codecamp.fitnessapp.ui.screens.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecamp.fitnessapp.data.weather.DefaultWeatherRepository
import com.codecamp.fitnessapp.location.LocationTrackerInterface
import com.codecamp.fitnessapp.model.Weather
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject constructor(
    private val weatherRepository: DefaultWeatherRepository,
    private val locationTracker: LocationTrackerInterface
) : ViewModel() {
    val weather = weatherRepository.weather
    init {
        fetchWeather()
    }

    fun fetchWeather() {
        viewModelScope.launch {
            val location = locationTracker.getLocation()
            location?.let {
                weatherRepository.fetchWeather(location.latitude,location.longitude)
                Log.d("WEATHER_DATA", location.latitude.toString() + " ** " + location.longitude.toString())
            }
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