package com.codecamp.fitnessapp

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
    val healthConnectClient: HealthConnectClient = HealthConnectClient.getOrCreate(app)

    /*fun getHealthConnectClient() {
        if(!HealthConnectClient.isApiSupported()) {
            return
        }

        if (HealthConnectClient.isProviderAvailable(app)) {
            healthConnectClient = HealthConnectClient.getOrCreate(app)
        }
    }*/

    suspend fun getStepsRecords(startTime: Instant, endTime: Instant): ReadRecordsResponse<StepsRecord>? {
        if (healthConnectClient == null) {
            return null
        }

        return healthConnectClient!!.readRecords(
            ReadRecordsRequest(
                StepsRecord::class,
                timeRangeFilter = TimeRangeFilter.before(startTime)
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
                timeRangeFilter = TimeRangeFilter.Companion.between(startTime, endTime)
            )
        )
    }

    suspend fun getDistanceRecords(startTime: Instant, endTime: Instant): ReadRecordsResponse<DistanceRecord>? {
        if (healthConnectClient == null) {
            return null
        }

        return healthConnectClient!!.readRecords(
            ReadRecordsRequest(
                DistanceRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )
    }

    suspend fun getExerciseSessionRecords(startTime: Instant, endTime: Instant): ReadRecordsResponse<ExerciseSessionRecord>? {
        /***
         *  useful constants:
         *      EXERCISE_TYPE_BIKING = 8
         *      EXERCISE_TYPE_HIKING = 37
         *      EXERCISE_TYPE_RUNNING = 56
         *      EXERCISE_TYPE_SQUAT = 67
         *
         *      EXERCISE_TYPE_OTHER_WORKOUT = 0
         */


        if (healthConnectClient == null) {
            return null
        }

        return healthConnectClient!!.readRecords(
            ReadRecordsRequest(
                    ExerciseSessionRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
            )
        )
    }
}
