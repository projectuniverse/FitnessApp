package com.codecamp.fitnessapp.ui.screens.inside

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecamp.fitnessapp.data.user.DefaultUserRepository
import com.codecamp.fitnessapp.data.workout.DefaultWorkoutRepository
import com.codecamp.fitnessapp.healthconnect.HealthConnectRepositoryInterface
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.User
import com.codecamp.fitnessapp.sensor.repository.GyroscopeRepository
import com.codecamp.fitnessapp.ui.screens.inside.insideutil.PushUpUtil
import com.codecamp.fitnessapp.ui.screens.inside.insideutil.SitUpUtil
import com.codecamp.fitnessapp.ui.screens.inside.insideutil.SquatUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsideViewModel
@Inject constructor(
    private val workoutRepository: DefaultWorkoutRepository,
    private val sitUpUtil: SitUpUtil,
    private val squatUtil: SquatUtil,
    private val pushUpUtil: PushUpUtil,
    private val userRepository: DefaultUserRepository,
    private val gyroscopeRepository: GyroscopeRepository,
    private val healthConnectRepository: HealthConnectRepositoryInterface,
) : ViewModel() {
    val user = userRepository.user

    private val gyroscopeData = gyroscopeRepository.gyroscopeData

    val timePassed: MutableLiveData<String> by lazy { MutableLiveData("00:00:00") }
    private var workingOut = false
    private var timerRunning = true
    private var startTime = 0L

    val repetitions: MutableLiveData<Int> by lazy { MutableLiveData(0) }


    init {
        viewModelScope.launch {
            healthConnectRepository.loadExercises()
        }
        viewModelScope.launch {
            /*
            * starts the timer thread
            * */
            Thread {
                run {
                    while (timerRunning) {
                        if(workingOut){
                            timePassed.postValue(
                                getElapsedTime(
                                    (System.currentTimeMillis()/1000).toInt(),
                                    (startTime/1000).toInt()
                                ))
                        }

                        Thread.sleep(200)
                    }
                }
            }.start()
        }
    }

    fun startListening(workoutType: String) {
        viewModelScope.launch {
            delay(3000L)
        }

        gyroscopeRepository.startListening()
    }

    fun stopListening() {
        gyroscopeRepository.stopListening()
    }

    fun updateRepetitions(workoutType: String) {
        repetitions.value =
            when (workoutType) {
                "Pushups" -> { pushUpUtil.checkPushUp(gyroscopeData.value) }
                "Situps" -> { sitUpUtil.checkRepetition(gyroscopeData.value) }
                else -> { squatUtil.checkRepetition(gyroscopeData.value) }
            }
    }

    /*
    * starts and stops the timer
    * */
    fun switchWorkingOut() {
        workingOut = !workingOut
        if(workingOut) {
            startTime = System.currentTimeMillis()
        } else {
            timerRunning = false
        }
    }

    /*
    * calculates the burned calories of the inside workouts
    * */
    fun calculateKCalInside(workoutName: String, repetitions: Int, user: User): Int {
        val kCal:Double = when (workoutName) {
            "Squats" -> {repetitions/25.0 * user.weight * 0.0175 * 5.5}
            "Pushups" -> {repetitions * user.weight * 0.2906}
            else -> {repetitions * 0.5 * user.weight/150}
        }
        return kCal.toInt()
    }

    fun saveInsideWorkout(result: InsideWorkout) {
        viewModelScope.launch {
            workoutRepository.insertInsideWorkout(result)
        }
    }

    /*
    * calculates from a given start and end time the format: hh:mm:ss
    * */
    fun getElapsedTime(endTime: Int, startTime: Int): String {
        val elapsedSeconds = endTime - startTime
        val elapsedMinutes = elapsedSeconds / 60
        val elapsedHours = elapsedMinutes / 60

        val secondsDisplay = if(elapsedSeconds % 60 < 10) { "0" + elapsedSeconds % 60 }
        else { elapsedSeconds % 60 }
        val minutsDisplay = if(elapsedMinutes % 60 < 10) { "0" + elapsedMinutes % 60 }
        else { elapsedMinutes % 60 }
        val hoursDisplay = if(elapsedHours < 10) { "0" + elapsedHours % 60 }
        else { ""+elapsedHours }

        return "$hoursDisplay:$minutsDisplay:$secondsDisplay"
    }

    fun resetRepetitions() {
        //set repetitions to 0 for UI reset
        pushUpUtil.repetitions.value = 0
        sitUpUtil.repetitions.value = 0
        squatUtil.repetitions.value = 0
    }
}