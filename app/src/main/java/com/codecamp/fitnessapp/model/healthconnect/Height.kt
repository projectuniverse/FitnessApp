package com.codecamp.fitnessapp.model.healthconnect

import androidx.health.connect.client.records.metadata.Metadata
import androidx.health.connect.client.units.Length
import java.time.Instant
import java.time.ZoneOffset

data class Height(
    val height: Length,
    val time: Instant,
    val zoneOffset: ZoneOffset?,
    val metadata: Metadata
)
