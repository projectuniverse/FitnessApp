package com.codecamp.fitnessapp.data.workout.outside_workout

import androidx.room.*
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout
import kotlinx.coroutines.flow.Flow

/*
 * Makes the requests to the Room database
 */
@Dao
interface OutsideWorkoutDao {
    //TODO Conflicts ignorieren?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(outsideWorkout: OutsideWorkout)

    @Query("SELECT * FROM outsideWorkout WHERE id = :id")
    fun getOutsideWorkout(id: Int): Flow<OutsideWorkout>

    @Query("SELECT * FROM outsideWorkout")
    fun getAllOutsideWorkouts(): Flow<List<OutsideWorkout>>
}