package com.codecamp.fitnessapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * Acts as the inside workout data stored in the Room database (squats, pushups, situps)
 */
@Entity(tableName = "insideWorkout")
data class InsideWorkout(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val repetitions: Int,
    //Time in unix, UTC
    val startTime: Int,
    //Time in unix, UTC
    val endTime: Int,
    val kcal: Int
)