package com.codecamp.fitnessapp.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationTrackerInterface {
    suspend fun getLocation(): Location?

    fun getLocationFlow(rate: Long): Flow<Location>
}