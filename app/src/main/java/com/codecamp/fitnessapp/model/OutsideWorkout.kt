package com.codecamp.fitnessapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * Acts as the outside workout data stored in the Room database (jogging, riding a bike, walking/hiking)
 */
@Entity(tableName = "outsideWorkout")
data class OutsideWorkout(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    // Average pace
    val pace: Double,
    val steps: Int,
    val distance: Double,
    //Time in unix, UTC
    val startTime: Int,
    //Time in unix, UTC
    val endTime: Int,
    val kcal: Int
)