package com.codecamp.fitnessapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecamp.fitnessapp.data.workout.DefaultWorkoutRepository
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel
@Inject constructor(
    private val workoutRepository: DefaultWorkoutRepository,
) : ViewModel() {
    val oldInsideWorkouts = workoutRepository.insideWorkouts
    val oldOutsideWorkouts = workoutRepository.outsideWorkouts

    fun getSortedSequenz(oldInsides: List<InsideWorkout>, oldOutsides: List<OutsideWorkout>): List<Int> {
        val sequence = mutableListOf<Int>()

        for (inside in oldInsides) {
            sequence.add(inside.endTime)
        }
        for (outside in oldOutsides) {
            sequence.add(outside.endTime)
        }

        sequence.sortDescending()

        return sequence
    }

    init {
        viewModelScope.launch {

        }
    }

}