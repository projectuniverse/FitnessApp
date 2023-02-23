package com.codecamp.fitnessapp.sensor

import android.content.Context
import javax.inject.Inject

class InsideWorkoutRepositoryImpl (
    val context: Context
): InsideWorkoutRepositoryInterface {
    override fun getProximitySensor(): MovementSensor {
        return ProximitySensor(context)
    }
}