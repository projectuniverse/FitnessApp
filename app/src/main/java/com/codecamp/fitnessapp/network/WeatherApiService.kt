package com.codecamp.fitnessapp.network

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
        @Query("appid") appid: String = "087957b907143749bd6d8c356211f383"
    ): DeserializedWeather
}
