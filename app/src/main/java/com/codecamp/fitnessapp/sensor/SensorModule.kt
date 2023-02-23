package com.codecamp.fitnessapp.sensor

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SensorModule {

    @Provides
    @Singleton
    fun provideProximitySensor(app: Application): ProximitySensor {
        return ProximitySensor(app)
    }

    @Provides
    @Singleton
    fun provideInsideWorkoutImpl(app: Application): InsideWorkoutRepositoryImpl {
        return InsideWorkoutRepositoryImpl(app)
    }
}