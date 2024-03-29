package com.codecamp.fitnessapp.di

import com.codecamp.fitnessapp.sensor.repository.GyroscopeRepository
import com.codecamp.fitnessapp.sensor.GyroscopeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class SensorRepoModule {

    @Binds
    @Singleton
    abstract fun bindGyroscopeRepo(gyroscopeRepositoryImpl: GyroscopeRepositoryImpl): GyroscopeRepository
}