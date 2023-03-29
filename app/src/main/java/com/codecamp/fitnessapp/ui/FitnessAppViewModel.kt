package com.codecamp.fitnessapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecamp.fitnessapp.data.Initialization.DefaultInitializationRepository
import com.codecamp.fitnessapp.data.user.DefaultUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FitnessAppViewModel
@Inject constructor(
    private val initializationRepository: DefaultInitializationRepository,
    private val userRepository: DefaultUserRepository
) : ViewModel() {
    val user = userRepository.user
    var firstInit = initializationRepository.firstInit

    init {
        viewModelScope.launch {
            initializationRepository.getFirstInit()
        }
    }

    fun setFirstInit(firstInit : Boolean) {
        viewModelScope.launch {
            initializationRepository.setFirstInit(firstInit)
        }
    }
}