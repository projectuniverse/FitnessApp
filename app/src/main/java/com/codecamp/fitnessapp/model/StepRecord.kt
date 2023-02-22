package com.codecamp.fitnessapp.model

import java.time.Instant
import java.time.ZoneOffset
import androidx.health.connect.client.records.metadata.Metadata

data class StepRecord(
    val count: Long,
    val startTime: Instant,
    val endTime: Instant,
    val startZoneOffset: ZoneOffset?,
    val endZoneOffset: ZoneOffset?,
    val metadata: Metadata
)
