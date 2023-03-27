package com.codecamp.fitnessapp.sensor

import com.codecamp.fitnessapp.sensor.finall.GyroscopeRepository
import com.codecamp.fitnessapp.sensor.finall.GyroscopeRepositoryImpl
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