package com.codecamp.fitnessapp.di

import com.codecamp.fitnessapp.healthconnect.HealthConnectRepositoryImpl
import com.codecamp.fitnessapp.healthconnect.HealthConnectRepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HealthConnectRepoModule {

    @Binds
    @Singleton
    abstract fun bindHealthConnectRepository(healthConnectRepositoryImpl: HealthConnectRepositoryImpl): HealthConnectRepositoryInterface
}