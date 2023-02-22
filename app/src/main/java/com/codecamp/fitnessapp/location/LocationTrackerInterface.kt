package com.codecamp.fitnessapp.location

import android.location.Location

interface LocationTrackerInterface {
    suspend fun getLocation(): Location?
}