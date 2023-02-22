package com.codecamp.fitnessapp.di

import android.app.Application
import com.codecamp.fitnessapp.data.weather.DefaultWeatherRepository
import com.codecamp.fitnessapp.data.weather.WeatherRepository
import com.codecamp.fitnessapp.location.LocationTrackerImplementation
import com.codecamp.fitnessapp.location.LocationTrackerInterface
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

}