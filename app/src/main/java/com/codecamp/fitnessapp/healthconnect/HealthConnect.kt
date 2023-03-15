package com.codecamp.fitnessapp.healthconnect

import android.app.Application
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.*
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.response.ReadRecordsResponse
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant
import javax.inject.Inject

class HealthConnect @Inject constructor(
    private val app: Application
) {
    var healthConnectClient: HealthConnectClient? = null

    fun getHealthConnectClient() {
        if (healthConnectClient != null) {
            return
        }

        if(!HealthConnectClient.isApiSupported()) {
            return
        }

        if (HealthConnectClient.isProviderAvailable(app)) {
            healthConnectClient = HealthConnectClient.getOrCreate(app)
        }
    }

    suspend fun getStepsRecords(startTime: Instant, endTime: Instant): ReadRecordsResponse<StepsRecord>? {
        getHealthConnectClient()
        if (healthConnectClient == null) {
            return null
        }
        return healthConnectClient!!.readRecords(
            ReadRecordsRequest(
                StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )
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
        getHealthConnectClient()

        if (healthConnectClient == null) {
            return null
        }

        return healthConnectClient!!.readRecords(
            ReadRecordsRequest(
                ExerciseSessionRecord::class,
                timeRangeFilter = TimeRangeFilter.before(Instant.now())
            )
        )
    }

    suspend fun getHeightRecords(startTime: Instant, endTime: Instant): ReadRecordsResponse<HeightRecord>? {
        if (healthConnectClient == null) {
            return null
        }
        return healthConnectClient!!.readRecords(
            ReadRecordsRequest(
                HeightRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )
    }

    suspend fun getWeightRecords(startTime: Instant, endTime: Instant): ReadRecordsResponse<WeightRecord>? {
        if (healthConnectClient == null) {
            return null
        }
        return healthConnectClient!!.readRecords(
            ReadRecordsRequest(
                WeightRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )
    }
}