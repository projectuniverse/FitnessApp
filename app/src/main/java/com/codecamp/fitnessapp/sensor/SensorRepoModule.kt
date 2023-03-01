package com.codecamp.fitnessapp.sensor

import com.codecamp.fitnessapp.sensor.pushup.PushUpRepositoryImpl
import com.codecamp.fitnessapp.sensor.pushup.PushUpRepository
import com.codecamp.fitnessapp.sensor.squat.SquatRepository
import com.codecamp.fitnessapp.sensor.squat.SquatRepositoryImpl
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
    abstract fun bindPushUpRepo(pushUpRepositoryImpl: PushUpRepositoryImpl): PushUpRepository

    @Binds
    @Singleton
    abstract fun bindSquatRepo(squatRepositoryImpl: SquatRepositoryImpl): SquatRepository
}