package com.codecamp.fitnessapp.healthconnect

import android.app.Application
import android.content.Context
import androidx.core.app.ActivityCompat
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.*
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.response.ReadRecordsResponse
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant
import javax.inject.Inject

class HealthConnect @Inject constructor(
    private val context: Context
) {
    var healthConnectClient: HealthConnectClient? = returnHealthConnectClient()
    private var lastRequestedInstantExercise: Instant? = null
    private val PERMISSIONS = setOf(
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getReadPermission(ActiveCaloriesBurnedRecord::class),
        HealthPermission.getReadPermission(WeightRecord::class),
        HealthPermission.getReadPermission(HeightRecord::class)
    )

    private suspend fun hasPermissions(): Boolean{
        if (healthConnectClient == null) {
            return false
        }

        if (healthConnectClient!!.permissionController.getGrantedPermissions().containsAll(PERMISSIONS)) {
            return true
        }

        return false
    }

    private fun returnHealthConnectClient(): HealthConnectClient? {
        if(!HealthConnectClient.isApiSupported()) {
            return null
        }

        if (HealthConnectClient.isProviderAvailable(context)) {
            return HealthConnectClient.getOrCreate(context)
        }

        return null
    }

    suspend fun getActiveCaloriesBurnedRecords(startTime: Instant, endTime: Instant): ReadRecordsResponse<ActiveCaloriesBurnedRecord>? {
        if (healthConnectClient == null) {
            return null
        }
        return healthConnectClient!!.readRecords(
            ReadRecordsRequest(
                ActiveCaloriesBurnedRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )
    }

    suspend fun getExerciseSessionRecords(): ReadRecordsResponse<ExerciseSessionRecord>? {
        /***
         *  useful constants:
         *      EXERCISE_TYPE_BIKING = 8
         *      EXERCISE_TYPE_HIKING = 37
         *      EXERCISE_TYPE_RUNNING = 56
         *      EXERCISE_TYPE_SQUAT = 67
         *
         *      EXERCISE_TYPE_OTHER_WORKOUT = 0
         */

        if (healthConnectClient == null || !hasPermissions()) {
            return null
        }

        if (lastRequestedInstantExercise == null) {
            lastRequestedInstantExercise = Instant.now()
            return healthConnectClient!!.readRecords(
                ReadRecordsRequest(
                    ExerciseSessionRecord::class,
                    timeRangeFilter = TimeRangeFilter.before(Instant.now())
                )
            )
        } else {
            return healthConnectClient!!.readRecords(
                ReadRecordsRequest(
                    ExerciseSessionRecord::class,
                    timeRangeFilter = TimeRangeFilter.Companion.after(lastRequestedInstantExercise!!)
                )
            )
        }
    }

    suspend fun getHeightRecords(): ReadRecordsResponse<HeightRecord>? {
        if (healthConnectClient == null || !hasPermissions()) {
            return null
        }
        return healthConnectClient!!.readRecords(
            ReadRecordsRequest(
                HeightRecord::class,
                timeRangeFilter = TimeRangeFilter.before(Instant.now())
            )
        )
    }

    suspend fun getWeightRecords(): ReadRecordsResponse<WeightRecord>? {
        if (healthConnectClient == null || !hasPermissions()) {
            return null
        }
        return healthConnectClient!!.readRecords(
            ReadRecordsRequest(
                WeightRecord::class,
                timeRangeFilter = TimeRangeFilter.before(Instant.now())
            )
        )
    }
}