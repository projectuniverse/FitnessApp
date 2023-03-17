package com.codecamp.fitnessapp.model.healthconnect

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "healthConnectUsage")
data class HealthConnectUsage(
    @PrimaryKey
    val id: Int = 0,
    val useHealthConnect: Boolean,
    val hasPermission: Boolean
)
