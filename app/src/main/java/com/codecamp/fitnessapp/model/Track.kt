package com.codecamp.fitnessapp.model

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

//TODO Neuer track für jeden Datenpunkt
@Entity(tableName = "track",
    foreignKeys = [ForeignKey(
        entity = OutsideWorkout::class,
        parentColumns = ["id"],
        childColumns = ["workoutId"],
        onDelete = CASCADE
    )]
)
data class Track(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //TODO Foreign key für automatisches löschen, sodass alle daten mit dem key gelöscht werden wenn workout gelöscht wird
    val workoutId: Int,
    // lat and long must later manually be converted to LatLng for using google maps
    // Also used for calculating pace per km in combination with timestamp
    val lat: Double,
    val long: Double,
    // Used for calculating altitude profile in combination with timestamp
    val altitude: Double,
    // UTC time
    val timestamp: Int
)