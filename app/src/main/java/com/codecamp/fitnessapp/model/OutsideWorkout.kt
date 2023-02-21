package com.codecamp.fitnessapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

/*
 * Acts as the outside workout data stored in the Room database (jogging, riding a bike, walking/hiking)
 */
@Entity(tableName = "outsideWorkout")
data class OutsideWorkout(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    // First value of LatLng tuple: latitude, second: longitude
    //val gpsPoints: MutableList<LatLng>,
    // Average pace
    val pace: Double,
    // First value of pair: UTC time, second: pace in km/h
    //val pacePerKm: MutableList<Pair<Int, Double>>,
    val steps: Int,
    val distance: Double,
    //Time in unix, UTC
    val startTime: Int,
    //Time in unix, UTC
    val endTime: Int,
    // First value of pair: UTC time, second: height (z-axis)
    //val altitudeProfile: MutableList<Pair<Int, Double>>,
    val kcal: Int
)