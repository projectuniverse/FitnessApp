package com.codecamp.fitnessapp.model.healthconnect

import java.time.Instant
import java.time.ZoneOffset
import androidx.health.connect.client.records.metadata.Metadata

data class Step(
    val count: Long,
    val startTime: Instant,
    val endTime: Instant,
    val startZoneOffset: ZoneOffset?,
    val endZoneOffset: ZoneOffset?,
    val metadata: Metadata
)
