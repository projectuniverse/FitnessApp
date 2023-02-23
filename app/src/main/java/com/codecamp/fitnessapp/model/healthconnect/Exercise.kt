package com.codecamp.fitnessapp.model.healthconnect

import androidx.health.connect.client.records.metadata.Metadata
import java.time.Instant
import java.time.ZoneOffset

data class Exercise(
    val exerciseType: Int,
    val title: String?,
    val notes: String?,
    val startTime: Instant,
    val endTime: Instant,
    val startZoneOffset: ZoneOffset?,
    val endZoneOffset: ZoneOffset?,
    val metadata: Metadata
)
