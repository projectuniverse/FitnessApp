package com.codecamp.fitnessapp.model.healthconnect

import androidx.health.connect.client.records.metadata.Metadata
import androidx.health.connect.client.units.Mass
import java.time.Instant
import java.time.ZoneOffset

data class Weight(
    val weight: Mass,
    val time: Instant,
    val zoneOffset: ZoneOffset?,
    val metadata: Metadata
)
