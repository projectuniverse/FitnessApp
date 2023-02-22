package com.codecamp.fitnessapp.model.healthconnect

import androidx.health.connect.client.records.metadata.Metadata
import androidx.health.connect.client.units.Length
import java.time.Instant
import java.time.ZoneOffset

data class Distance(
    val distance: Length,
    val startTime: Instant,
    val endTime: Instant,
    val startZoneOffset: ZoneOffset?,
    val endZoneOffset: ZoneOffset?,
    val metadata: Metadata
)
