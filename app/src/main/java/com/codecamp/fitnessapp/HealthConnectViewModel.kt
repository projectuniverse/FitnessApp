package com.codecamp.fitnessapp

import androidx.activity.ComponentActivity
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.response.ReadRecordsResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codecamp.fitnessapp.model.StepRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class HealthConnectViewModel @Inject constructor(
    val healthConnect: HealthConnect
): ViewModel() {
    val stepRecordList = mutableListOf<StepRecord>()

    init {
        //healthConnect.getHealthConnectClient()
    }

    fun getStepsRecords() {
        viewModelScope.launch {
            val response = healthConnect.getStepsRecords(Instant.now(), Instant.now())
            if (response != null) {
                for (record in response.records) {
                    stepRecordList.add(StepRecord(
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
}