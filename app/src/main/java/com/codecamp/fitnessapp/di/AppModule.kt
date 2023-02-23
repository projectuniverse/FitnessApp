package com.codecamp.fitnessapp.di

import android.app.Application
import androidx.room.Room
import com.codecamp.fitnessapp.data.track.DefaultTrackRepository
import com.codecamp.fitnessapp.data.track.TrackDatabase
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

/*
 * Dependency injection using Hilt.
 * Used to get the respective repositories.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    @kotlinx.serialization.ExperimentalSerializationApi
    fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory(MediaType.get("application/json")))
            .baseUrl("https://api.openweathermap.org/")
            .build()
    }

    @Provides
    @Singleton
    @kotlinx.serialization.ExperimentalSerializationApi
    fun getRetrofitService(retrofit: Retrofit) : WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserDatabase(app: Application): UserDatabase {
        return Room.databaseBuilder(
            app,
            UserDatabase::class.java,
            "user_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        db: UserDatabase
    ): DefaultUserRepository {
        return DefaultUserRepository(db.userDao())
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(app: Application): WeatherDatabase {
        return Room.databaseBuilder(
            app,
            WeatherDatabase::class.java,
            "weather_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        retrofitservice: WeatherApiService,
        db: WeatherDatabase
    ): DefaultWeatherRepository {
        return DefaultWeatherRepository(retrofitservice, db.weatherDao())
    }

    @Provides
    @Singleton
    fun provideInsideWorkoutDatabase(app: Application): InsideWorkoutDatabase {
        return Room.databaseBuilder(
            app,
            InsideWorkoutDatabase::class.java,
            "inside_workout_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideOutsideWorkoutDatabase(app: Application): OutsideWorkoutDatabase {
        return Room.databaseBuilder(
            app,
            OutsideWorkoutDatabase::class.java,
            "outside_workout_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideWorkoutRepository(
        insideWorkoutDatabase: InsideWorkoutDatabase,
        outsideWorkoutDatabase: OutsideWorkoutDatabase
    ): DefaultWorkoutRepository {
        return DefaultWorkoutRepository(
            insideWorkoutDatabase.insideWorkoutDao(),
            outsideWorkoutDatabase.outsideWorkoutDao()
        )
    }

    @Provides
    @Singleton
    fun provideTrackDatabase(app: Application): TrackDatabase {
        return Room.databaseBuilder(
            app,
            TrackDatabase::class.java,
            "track_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideTrackRepository(
        db: TrackDatabase
    ): DefaultTrackRepository {
        return DefaultTrackRepository(db.trackDao())
    }
}