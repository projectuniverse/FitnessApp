package com.codecamp.fitnessapp.data.healthconnect

import com.codecamp.fitnessapp.model.healthconnect.HealthConnectUsage

interface HealthConnectUsageRepository {
    suspend fun insertHealthConnectUsage(healthConnectUsage: HealthConnectUsage)
}

@kotlinx.serialization.ExperimentalSerializationApi
class DefaultHealthConnectUsageRepository(
    private val hcUsageDao: HealthConnectUsageDao
): HealthConnectUsageRepository {

    val hcUsages = hcUsageDao.getHealthConnectUsage()

    override suspend fun insertHealthConnectUsage(healthConnectUsage: HealthConnectUsage) = hcUsageDao.insert(healthConnectUsage)
}