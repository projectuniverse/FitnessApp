package com.codecamp.fitnessapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    //private val userRepository: DefaultUserRepository,
) : ViewModel() {

    private fun isValidNumber(case: Int, input: String): Boolean {

        val inputNumber = input.toIntOrNull()

        return if(inputNumber != null) {
            when (case) {
                0 -> {
                    !(inputNumber < 0 || inputNumber > 100)
                }
                1 -> {
                    !(inputNumber < 50 || inputNumber > 300)
                }
                else -> {
                    !(inputNumber < 20 || inputNumber > 400)
                }
            }
        } else {
            false
        }

    }

    fun isValidUser(age: String, height: String, weight: String): Boolean {
        return (isValidNumber(0, age)
                && isValidNumber(1, height)
                && isValidNumber(2, weight))
    }

    //val user = userRepository.user

    init {
        viewModelScope.launch {

        }
    }
}