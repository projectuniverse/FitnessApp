package com.codecamp.fitnessapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

/*
 * Acts as the track data stored in the Room database.
 * Used to get gps location and altitude.
 * Used to calculate gps location timeline for google maps,
 * as well as the altitude profile and pace per km.
 */

/*
@Entity(tableName = "track",
    foreignKeys = arrayOf(ForeignKey(
        entity = OutsideWorkout::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("workoutId"),
        onDelete = CASCADE
    ))
)

 */
@Entity(tableName = "track")
data class Track(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    /*
     * Foreign key pointing to the outsideWorkout with this id.
     * If outsideWorkout is deleted, all Tracks with this workoutId automatically
     * get deleted.
     */
    val workoutId: Int,
    // lat and long must later manually be converted to LatLng for using google maps
    // Also used for calculating pace per km in combination with timestamp
    val lat: Double,
    val long: Double,
    // Used for calculating altitude profile in combination with timestamp
    val altitude: Double,
    // UTC time in millis
    val timestamp: Long
)