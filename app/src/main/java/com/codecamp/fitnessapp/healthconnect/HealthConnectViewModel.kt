package com.codecamp.fitnessapp.healthconnect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecamp.fitnessapp.data.healthconnect.DefaultHealthConnectUsageRepository
import com.codecamp.fitnessapp.model.healthconnect.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class HealthConnectViewModel @Inject constructor(
    val healthConnect: HealthConnect,
    private val hcUsageRepository: DefaultHealthConnectUsageRepository
): ViewModel() {
    val hcUsage = hcUsageRepository.hcUsages

    init {
        //healthConnect.getHealthConnectClient()
    }

    fun updatePermissionGranted(usage: Boolean, permission: Boolean) {
        viewModelScope.launch {
            hcUsageRepository.insertHealthConnectUsage(
                HealthConnectUsage(
                    0,
                    useHealthConnect = usage,
                    hasPermission = permission
                )
            )
        }
    }
}