package com.codecamp.fitnessapp.data.weather

import android.util.Log
import com.codecamp.fitnessapp.data.weather.WeatherDao
import com.codecamp.fitnessapp.model.DeserializedWeather
import com.codecamp.fitnessapp.model.Weather
import com.codecamp.fitnessapp.network.WeatherApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

/*
 * Acts as a single source of truth. Returns online and offline weather.
 */
interface WeatherRepository {
    suspend fun fetchWeather(
        latitude: Double,
        longitude: Double
    )
}

@kotlinx.serialization.ExperimentalSerializationApi
class DefaultWeatherRepository(
    private val weatherApiService: WeatherApiService,
    private val weatherDao: WeatherDao
) : WeatherRepository {
    /*
     * Acts as a reference to the weather stored in the database.
     * This state flow is viewed by the viewmodel.
     */
    val weather = weatherDao.getWeather()

    override suspend fun fetchWeather(
        latitude: Double,
        longitude: Double
    ) {
        try {
            // If there is any error with catching the data, an exception is thrown. Thus, data always != null when inserted.
            val deserializedWeather = weatherApiService.getWeather(latitude, longitude)
            val deserializedForecast = weatherApiService.getWeatherForecast(latitude, longitude)
            val weather = Weather(
                weatherType = deserializedWeather.weather[0].main,
                temperature = deserializedWeather.main.temp,
                windSpeed = deserializedWeather.wind.speed,
                time = deserializedWeather.dt,
                sunrise = deserializedWeather.sys.sunrise,
                sunset = deserializedWeather.sys.sunset,
                cityName = deserializedWeather.name,
                weatherForecast = deserializedForecast.list[0].weather[0].main
            )
            weatherDao.insert(weather)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("WeatherRepo", "IOException")
            return
        } catch (e: HttpException) {
            Log.d("WeatherRepo", "HttpException")
            return
        }
    }
}