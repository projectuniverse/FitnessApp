package com.codecamp.fitnessapp.network

import com.codecamp.fitnessapp.BuildConfig
import com.codecamp.fitnessapp.model.DeserializedForecast
import com.codecamp.fitnessapp.model.DeserializedWeather
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * Makes the request to the weather server
 */
interface WeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = BuildConfig.OPEN_WEATHER_API_KEY
    ): DeserializedWeather

    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = BuildConfig.OPEN_WEATHER_API_KEY,
        @Query("cnt") cnt: Int = 1
    ): DeserializedForecast
}
