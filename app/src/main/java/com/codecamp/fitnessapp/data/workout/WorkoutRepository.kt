package com.codecamp.fitnessapp.data.workout

import com.codecamp.fitnessapp.data.workout.inside_workout.InsideWorkoutDao
import com.codecamp.fitnessapp.data.workout.outside_workout.OutsideWorkoutDao
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout
import kotlinx.coroutines.flow.Flow

/*
 * Acts as a single source of truth. Returns workout data.
 */
interface WorkoutRepository {
    fun getInsideWorkout(): Flow<List<InsideWorkout>>
    fun getOutsideWorkout(): Flow<List<OutsideWorkout>>
    suspend fun insertInsideWorkout(insideWorkout: InsideWorkout)
    suspend fun insertOutsideWorkout(outsideWorkout: OutsideWorkout)
}

@kotlinx.serialization.ExperimentalSerializationApi
class DefaultWorkoutRepository(
    private val insideWorkoutDao: InsideWorkoutDao,
    private val outsideWorkoutDao: OutsideWorkoutDao
) : WorkoutRepository {
    // TODO oder return Flow<List<InsideWorkout>>?
    override fun getInsideWorkout(): Flow<List<InsideWorkout>> = insideWorkoutDao.getAllInsideWorkouts()
    override fun getOutsideWorkout(): Flow<List<OutsideWorkout>> = outsideWorkoutDao.getAllOutsideWorkouts()
    override suspend fun insertInsideWorkout(insideWorkout: InsideWorkout) = insideWorkoutDao.insert(insideWorkout)
    override suspend fun insertOutsideWorkout(outsideWorkout: OutsideWorkout) = outsideWorkoutDao.insert(outsideWorkout)
}