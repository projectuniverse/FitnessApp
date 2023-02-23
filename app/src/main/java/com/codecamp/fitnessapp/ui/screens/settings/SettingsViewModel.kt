package com.codecamp.fitnessapp.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecamp.fitnessapp.data.user.DefaultUserRepository
import com.codecamp.fitnessapp.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val userRepository: DefaultUserRepository,
) : ViewModel() {
    val user = userRepository.user

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
        // Write to database
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
}