package com.codecamp.fitnessapp.healthconnect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecamp.fitnessapp.model.healthconnect.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class HealthConnectViewModel @Inject constructor(
    val healthConnect: HealthConnect
): ViewModel() {
    val stepRecordList = mutableListOf<Step>()
    val activeCaloriesBurnedList = mutableListOf<ActiveCaloriesBurned>()
    val heightList = mutableListOf<Height>()
    val weightList = mutableListOf<Weight>()
    val exerciseList = mutableListOf<Exercise>()

    init {
        healthConnect.getHealthConnectClient()
    }

    fun updateStepList() {
        viewModelScope.launch {
            val response = healthConnect.getStepsRecords(Instant.now(), Instant.now())
            if (response != null) {
                for (record in response.records) {
                    stepRecordList.add(Step(
                        record.count,
                        record.startTime,
                        record.endTime,
                        record.startZoneOffset,
                        record.endZoneOffset,
                        record.metadata))
                }
            }
        }
    }

    fun updateActiveCaloriesBurnedList(startTime: Instant, endTime: Instant) {
        viewModelScope.launch {
            val response = healthConnect.getActiveCaloriesBurnedRecords(startTime, endTime)
            if (response != null) {
                for (record in response.records) {
                    activeCaloriesBurnedList.add(
                        ActiveCaloriesBurned(
                            record.energy,
                            record.startTime,
                            record.endTime,
                            record.startZoneOffset,
                            record.endZoneOffset,
                            record.metadata
                        )
                    )
                }
            }
        }
    }

    fun updateHeightList(startTime: Instant, endTime: Instant) {
        viewModelScope.launch {
            val response = healthConnect.getHeightRecords(startTime, endTime)
            if (response != null) {
                for (record in response.records) {
                    heightList.add(
                        Height(
                            record.height,
                            record.time,
                            record.zoneOffset,
                            record.metadata
                        )
                    )
                }
            }
        }
    }

    fun updateWeightList(startTime: Instant, endTime: Instant) {
        viewModelScope.launch {
            val response = healthConnect.getWeightRecords(startTime, endTime)
            if (response != null) {
                for (record in response.records) {
                    weightList.add(
                        Weight(
                            record.weight,
                            record.time,
                            record.zoneOffset,
                            record.metadata
                        )
                    )
                }
            }
        }
    }

    fun updateExerciseList(startTime: Instant, endTime: Instant) {
        viewModelScope.launch {
            val response = healthConnect.getExerciseSessionRecords()
            if (response != null) {
                for (record in response.records) {
                    exerciseList.add(
                        Exercise(
                            record.exerciseType,
                            record.title,
                            record.notes,
                            record.startTime,
                            record.endTime,
                            record.startZoneOffset,
                            record.endZoneOffset,
                            record.metadata
                        )
                    )
                }
            }
        }
    }
}