package com.codecamp.fitnessapp

import androidx.lifecycle.ViewModel
import com.codecamp.fitnessapp.data.user.DefaultUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FitnessAppViewModel
@Inject constructor(
    private val userRepository: DefaultUserRepository,
) : ViewModel() {
    val user = userRepository.user
}