package com.codecamp.fitnessapp.di

import com.codecamp.fitnessapp.location.LocationTrackerImplementation
import com.codecamp.fitnessapp.location.LocationTrackerInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationTrackerModule {
    @Binds
    @Singleton
    abstract fun bindLocationTracker(locationTrackerImplementation: LocationTrackerImplementation): LocationTrackerInterface
}