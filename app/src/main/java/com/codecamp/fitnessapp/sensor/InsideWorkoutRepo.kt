package com.codecamp.fitnessapp.sensor

import javax.inject.Inject

interface InsideWorkoutRepositoryInterface {
    fun getProximitySensor(): MovementSensor
}