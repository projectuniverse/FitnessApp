package com.codecamp.fitnessapp.sensor

import javax.inject.Inject

interface InsideWorkoutRepository {
    fun getSensorData(): List<Float>
}

class DefaultInsideWorkoutRepository @Inject constructor(
    private var sensor: MovementSensor
): InsideWorkoutRepository {

    override fun getSensorData(): List<Float> {
        TODO("Not yet implemented")
    }

}