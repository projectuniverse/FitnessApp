package com.codecamp.fitnessapp.healthconnect

import androidx.health.connect.client.records.ExerciseSessionRecord
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class HealthConnectRepositoryImpl @Inject constructor(
    private val healthConnect: HealthConnect
): HealthConnectRepositoryInterface {
    //Inside Workout(s)
    override val squatExercises: MutableStateFlow<List<InsideWorkout>> = MutableStateFlow(emptyList())

    //Outside Workouts
    override val runningExercises: MutableStateFlow<List<OutsideWorkout>> = MutableStateFlow(emptyList())
    override val hikingExercises: MutableStateFlow<List<OutsideWorkout>> = MutableStateFlow(emptyList())
    override val bikingExercises: MutableStateFlow<List<OutsideWorkout>> = MutableStateFlow(emptyList())

    //User Data
    override val weight: MutableStateFlow<Int> = MutableStateFlow(-1)
    override val height: MutableStateFlow<Int> = MutableStateFlow(-1)

    override suspend fun loadExercises() {
        //get session records
        val exerciseRecords = healthConnect.getExerciseSessionRecords() ?: return

        //go through records
        //sort records in correct list

        for (record in exerciseRecords.records) {
            when(record.exerciseType) {
                ExerciseSessionRecord.EXERCISE_TYPE_SQUAT -> {
                    val newInsideWorkout = InsideWorkout(0, "Squats", 0, 0, 0, 0)
                    squatExercises.value += newInsideWorkout
                }
                ExerciseSessionRecord.EXERCISE_TYPE_BIKING -> {
                    val newOutsideWorkout = OutsideWorkout(0, "Biking", 0.0, 0, 0.0, 0, 0, 0)
                    bikingExercises.value += newOutsideWorkout
                }
                ExerciseSessionRecord.EXERCISE_TYPE_HIKING -> {
                    val newOutsideWorkout = OutsideWorkout(0, "Hiking", 0.0, 0, 0.0, 0, 0, 0)
                    hikingExercises.value += newOutsideWorkout
                }
                ExerciseSessionRecord.EXERCISE_TYPE_RUNNING -> {
                    val newOutsideWorkout = OutsideWorkout(0, "Running", 0.0, 0, 0.0, 0, 0, 0)
                    runningExercises.value += newOutsideWorkout
                }
                else -> { }
            }
        }
    }

    override suspend fun loadUserData() {
        val weightRecords = healthConnect.getWeightRecords()
        val heightRecords = healthConnect.getHeightRecords()

        if (weightRecords != null) {
            weight.value = weightRecords.records.last().weight.inKilograms.toInt()
        }
        if (heightRecords != null) {
            height.value = heightRecords.records.last().height.inMeters.toInt() / 100
        }
    }
}