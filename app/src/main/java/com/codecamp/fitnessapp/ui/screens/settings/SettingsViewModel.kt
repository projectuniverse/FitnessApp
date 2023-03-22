package com.codecamp.fitnessapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecamp.fitnessapp.data.healthconnect.DefaultHealthConnectUsageRepository
import com.codecamp.fitnessapp.data.user.DefaultUserRepository
import com.codecamp.fitnessapp.healthconnect.HealthConnectRepositoryInterface
import com.codecamp.fitnessapp.model.User
import com.codecamp.fitnessapp.model.healthconnect.HealthConnectUsage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val userRepository: DefaultUserRepository,
    private val hcUsageRepository: DefaultHealthConnectUsageRepository,
    private val healthConnectRepository: HealthConnectRepositoryInterface
) : ViewModel() {
    val user = userRepository.user
    val hcPermission = hcUsageRepository.hcUsages

    val healthConnectWeight = healthConnectRepository.weight
    val healthConnectHeight = healthConnectRepository.height

    private fun isValidNumber(case: Int, input: String): Boolean {
        val inputNumber = input.toIntOrNull()

        return if(inputNumber != null) {
            when (case) {
                0 -> {
                    !(inputNumber < 1 || inputNumber > 120)
                }
                1 -> {
                    !(inputNumber < 50 || inputNumber > 300)
                }
                else -> {
                    !(inputNumber < 10 || inputNumber > 400)
                }
            }
        } else {
            false
        }
    }

    fun updateHealthConnectUsage() {
        viewModelScope.launch {
            //val permission = hcPermission.first().hasPermission
            hcUsageRepository.insertHealthConnectUsage(HealthConnectUsage(0, true, true))
        }
    }

    fun isValidUser(age: String, height: String, weight: String): Boolean {
        // Writes to database if all three entered values are correct
        if (isValidNumber(0, age)
            && isValidNumber(1, height)
            && isValidNumber(2, weight)) {
            val user = User(0, age.toInt(), height.toInt(), weight.toInt())
            viewModelScope.launch {
                userRepository.insertUser(user)
            }
            return true
        }
        return false
    }

    fun checkForHealthConnectData(age: String, height: String, weight: String): Boolean {
        var tempHeight = if (height == "") 0 else height.toInt()
        var tempWeight = if (weight == "") 0 else weight.toInt()

        viewModelScope.launch {
            healthConnectRepository.loadUserData()

            if (isValidNumber(1, healthConnectHeight.value.toString())) {
                tempHeight = healthConnectHeight.value
            }
            if (isValidNumber(2, healthConnectWeight.value.toString())) {
                tempWeight = healthConnectWeight.value
            }
        }
        return isValidUser(age, tempHeight.toString(), tempWeight.toString())
    }
}