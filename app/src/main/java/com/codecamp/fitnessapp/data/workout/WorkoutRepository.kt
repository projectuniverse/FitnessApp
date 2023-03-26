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
    suspend fun insertInsideWorkout(insideWorkout: InsideWorkout)
    suspend fun insertOutsideWorkout(outsideWorkout: OutsideWorkout) : Int
}

@kotlinx.serialization.ExperimentalSerializationApi
class DefaultWorkoutRepository(
    private val insideWorkoutDao: InsideWorkoutDao,
    private val outsideWorkoutDao: OutsideWorkoutDao
) : WorkoutRepository {
    /*
     * Acts as a reference to the inside workouts stored in the database.
     * This state flow is viewed by the viewmodel.
     */
    val insideWorkouts = insideWorkoutDao.getAllInsideWorkouts()

    /*
     * Acts as a reference to the outside workouts stored in the database.
     * This state flow is viewed by the viewmodel.
     */
    val outsideWorkouts = outsideWorkoutDao.getAllOutsideWorkouts()

    override suspend fun insertInsideWorkout(insideWorkout: InsideWorkout) = insideWorkoutDao.insert(insideWorkout)
    override suspend fun insertOutsideWorkout(outsideWorkout: OutsideWorkout) : Int {
        return outsideWorkoutDao.insert(outsideWorkout).toInt()
    }
}