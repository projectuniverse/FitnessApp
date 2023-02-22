package com.codecamp.fitnessapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
 * Acts as the data stored in the Room database
 */
@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey
    val id: Int = 0,
    val weatherType: String,
    val temperature: Double,
    val windSpeed: Double,
    //Time of data calculation, unix, UTC
    val time: Int,
    //Time of sunrise, sunset both unix
    val sunrise: Int,
    val sunset: Int,
    val cityName: String,
    // Weather within the next 3h
    val weatherForecast: String
)

@Serializable
data class DeserializedForecast(
    @SerialName("list")
    val list: List<ForecastData>,
)

@Serializable
data class ForecastData(
    @SerialName("weather")
    val weather: List<WeatherData>,
)

@Serializable
data class DeserializedWeather(
    @SerialName("weather")
    val weather: List<WeatherData>,
    @SerialName("main")
    val main: MainData,
    @SerialName("wind")
    val wind: WindData,
    val dt: Int,
    @SerialName("sys")
    val sys: SysData,
    val name: String
)

@Serializable
data class WeatherData(
    @SerialName("main")
    val main: String
)

@Serializable
data class MainData(
    @SerialName("temp")
    val temp: Double
)

@Serializable
data class WindData(
    @SerialName("speed")
    val speed: Double
)

@Serializable
data class SysData(
    @SerialName("sunrise")
    val sunrise: Int,
    @SerialName("sunset")
    val sunset: Int
)