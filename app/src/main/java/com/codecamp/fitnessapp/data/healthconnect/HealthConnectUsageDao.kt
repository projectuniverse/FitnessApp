package com.codecamp.fitnessapp.data.healthconnect

import androidx.room.*
import com.codecamp.fitnessapp.model.healthconnect.HealthConnectUsage
import kotlinx.coroutines.flow.Flow


@Dao
interface HealthConnectUsageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(healthConnectUsage: HealthConnectUsage)

    @Query("SELECT * FROM healthConnectUsage WHERE id = 0")
    fun getHealthConnectUsage(): Flow<HealthConnectUsage>
}