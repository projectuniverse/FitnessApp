package com.codecamp.fitnessapp.di

import android.app.Application
import com.codecamp.fitnessapp.healthconnect.HealthConnect
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HealthConnectModule {

    @Provides
    @Singleton
    fun provideHealthConnect(app: Application) : HealthConnect {
        return HealthConnect(app)
    }
}