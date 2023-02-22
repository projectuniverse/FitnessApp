package com.codecamp.fitnessapp.data

import android.content.Context
import com.codecamp.fitnessapp.data.user.DefaultUserRepository
import com.codecamp.fitnessapp.data.user.UserDatabase
import com.codecamp.fitnessapp.data.user.UserRepository
import com.codecamp.fitnessapp.data.weather.DefaultWeatherRepository
import com.codecamp.fitnessapp.data.weather.WeatherDatabase
import com.codecamp.fitnessapp.data.weather.WeatherRepository
import com.codecamp.fitnessapp.data.workout.DefaultWorkoutRepository
import com.codecamp.fitnessapp.data.workout.WorkoutRepository
import com.codecamp.fitnessapp.data.workout.inside_workout.InsideWorkoutDatabase
import com.codecamp.fitnessapp.data.workout.outside_workout.OutsideWorkoutDatabase
import com.codecamp.fitnessapp.network.WeatherApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit

// App Container for dependency injection
interface AppContainer {
    val weatherRepository: WeatherRepository
    val workoutRepository: WorkoutRepository
    val userRepository: UserRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    private val BASE_URL = "https://api.openweathermap.org"

    @kotlinx.serialization.ExperimentalSerializationApi
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory(MediaType.get("application/json")))
        .baseUrl(BASE_URL)
        .build()

    @kotlinx.serialization.ExperimentalSerializationApi
    private val retrofitService : WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }

    @kotlinx.serialization.ExperimentalSerializationApi
    override val weatherRepository: WeatherRepository by lazy {
        DefaultWeatherRepository(
            retrofitService,
            WeatherDatabase.getDatabase(context).weatherDao()
        )
    }

    @kotlinx.serialization.ExperimentalSerializationApi
    override val workoutRepository: WorkoutRepository by lazy {
        DefaultWorkoutRepository(
            InsideWorkoutDatabase.getDatabase(context).insideWorkoutDao(),
            OutsideWorkoutDatabase.getDatabase(context).outsideWorkoutDao()
        )
    }

    @kotlinx.serialization.ExperimentalSerializationApi
    override val userRepository: UserRepository by lazy {
        DefaultUserRepository(
            UserDatabase.getDatabase(context).userDao()
        )
    }
}