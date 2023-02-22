package com.codecamp.fitnessapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.codecamp.fitnessapp.FitnessApplication
import com.codecamp.fitnessapp.data.user.UserRepository
import com.codecamp.fitnessapp.data.workout.WorkoutRepository

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FitnessApplication)
                val userRepository = application.container.userRepository
                UserViewModel(
                    userRepository = userRepository
                )
            }
        }
    }
}