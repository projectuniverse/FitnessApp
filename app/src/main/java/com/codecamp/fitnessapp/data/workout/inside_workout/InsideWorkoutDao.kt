package com.codecamp.fitnessapp.data.workout.inside_workout

import androidx.room.*
import com.codecamp.fitnessapp.model.InsideWorkout
import kotlinx.coroutines.flow.Flow

/*
 * Makes the requests to the Room database
 */
@Dao
interface InsideWorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(insideWorkout: InsideWorkout)

    @Query("SELECT * FROM insideWorkout WHERE id = :id")
    fun getInsideWorkout(id: Int): Flow<InsideWorkout>

    @Query("SELECT * FROM insideWorkout")
    fun getAllInsideWorkouts(): Flow<List<InsideWorkout>>

    @Query("DELETE FROM insideWorkout")
    fun deleteInsideWorkouts()
}