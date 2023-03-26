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
    val kcal: Int,
    // UTC time
    val startTime: Int,
    val endTime: Int
)