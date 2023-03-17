package com.codecamp.fitnessapp.healthconnect

import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout
import kotlinx.coroutines.flow.MutableStateFlow

interface HealthConnectRepositoryInterface {
    //Inside Workout(s)
    val squatExercises: MutableStateFlow<List<InsideWorkout>>

    //Outside Workouts
    val runningExercises: MutableStateFlow<List<OutsideWorkout>>
    val hikingExercises: MutableStateFlow<List<OutsideWorkout>>
    val bikingExercises: MutableStateFlow<List<OutsideWorkout>>

    //User Data
    val weight: MutableStateFlow<Int>
    val height: MutableStateFlow<Int>

    suspend fun loadExercises()

    suspend fun loadUserData()
}