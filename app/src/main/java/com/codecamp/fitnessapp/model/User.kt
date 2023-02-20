package com.codecamp.fitnessapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * Acts as the outside workout data stored in the Room database (jogging, riding a bike, walking/hiking)
 */
@Entity(tableName = "user")
data class User(
    @PrimaryKey
    val id: Int = 0,
    val age: Int,
    val height: Int,
    val weight: Int
)