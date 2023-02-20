package com.codecamp.fitnessapp.data.workout.inside_workout

import androidx.room.*
import com.codecamp.fitnessapp.model.InsideWorkout
import kotlinx.coroutines.flow.Flow

/*
 * Makes the requests to the Room database
 */
@Dao
interface InsideWorkoutDao {
    //TODO Conflicts ignorieren?
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(insideWorkout: InsideWorkout)

    @Update
    suspend fun update(insideWorkout: InsideWorkout)

    @Query("SELECT * FROM insideWorkout WHERE id = :id")
    fun getInsideWorkout(id: Int): Flow<InsideWorkout>

    @Query("SELECT EXISTS(SELECT * FROM insideWorkout)")
    fun insideWorkoutExists(): Flow<Boolean>

    @Query("SELECT * FROM insideWorkout")
    fun getAllInsideWorkouts(): Flow<List<InsideWorkout>>
}