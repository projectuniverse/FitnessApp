package com.codecamp.fitnessapp.model.healthconnect

import androidx.health.connect.client.units.Energy
import java.time.Instant
import java.time.ZoneOffset
import androidx.health.connect.client.records.metadata.Metadata

data class ActiveCaloriesBurned(
    val energy: Energy,
    val startTime: Instant,
    val endTime: Instant,
    val startZoneOffset: ZoneOffset?,
    val endZoneOffset: ZoneOffset?,
    val metadata: Metadata
)
