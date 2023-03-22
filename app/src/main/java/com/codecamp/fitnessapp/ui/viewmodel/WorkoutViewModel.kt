package com.codecamp.fitnessapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecamp.fitnessapp.data.track.DefaultTrackRepository
import com.codecamp.fitnessapp.data.user.DefaultUserRepository
import com.codecamp.fitnessapp.data.workout.DefaultWorkoutRepository
import com.codecamp.fitnessapp.healthconnect.HealthConnectRepositoryInterface
import com.codecamp.fitnessapp.location.LocationTrackerImplementation
import com.codecamp.fitnessapp.location.LocationTrackerInterface
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout
import com.codecamp.fitnessapp.model.Track
import com.codecamp.fitnessapp.model.User
import com.codecamp.fitnessapp.sensor.pushup.PushUpRepository
import com.codecamp.fitnessapp.sensor.pushup.PushUpUtil
import com.codecamp.fitnessapp.sensor.situp.SitUpRepository
import com.codecamp.fitnessapp.sensor.situp.SitUpUtil
import com.codecamp.fitnessapp.sensor.squat.SquatRepository
import com.codecamp.fitnessapp.sensor.squat.SquatUtil
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel
@Inject constructor(
    private val workoutRepository: DefaultWorkoutRepository,
    private val sitUpUtil: SitUpUtil,
    private val squatUtil: SquatUtil,
    private val pushUpUtil: PushUpUtil,
    private val squatRepository: SquatRepository,
    private val sitUpRepository: SitUpRepository,
    private val pushUpRepository: PushUpRepository,
    private val userRepository: DefaultUserRepository,
    private val healthConnectRepository: HealthConnectRepositoryInterface,
    private val trackRepository: DefaultTrackRepository,
    private val locationTracker: LocationTrackerInterface
) : ViewModel() {
    val oldInsideWorkouts = workoutRepository.insideWorkouts
    val oldOutsideWorkouts = workoutRepository.outsideWorkouts
    val user = userRepository.user

    val healthConnectSquats = healthConnectRepository.squatExercises
    val healthConnectRuns = healthConnectRepository.runningExercises
    val healthConnectBiking = healthConnectRepository.bikingExercises
    val healthConnectHikes = healthConnectRepository.hikingExercises

    private val squatSensorData = squatRepository.accelerometerData
    private val sitUpSensorData = sitUpRepository.gyroscopeData
    private val pushUpSensorData = pushUpRepository.proximitySensorData

    private val trackList = mutableListOf<Track>()

    val timePassed: MutableLiveData<String> by lazy { MutableLiveData("00:00:00") }
    var workingOut = false
    var timerRunning = true
    var startTime = 0L

    val repetitions: MutableLiveData<Int> by lazy { MutableLiveData(0) }

    fun startListening(workoutType: String) {
        when (workoutType) {
            "Pushups" -> {
                pushUpRepository.startListening()
                sitUpRepository.stopListening()
                squatRepository.stopListening()
            }
            "Situps" -> {
                sitUpRepository.startListening()
                pushUpRepository.stopListening()
                squatRepository.stopListening()
            }
            else -> {
                squatRepository.startListening()
                sitUpRepository.stopListening()
                pushUpRepository.stopListening()
            }
        }
    }

    fun stopListening() {
        pushUpRepository.stopListening()
        sitUpRepository.stopListening()
        squatRepository.stopListening()
    }

    fun updateRepetitions(workoutType: String) {
        repetitions.value =
            when (workoutType) {
                "Pushups" -> {
                    pushUpUtil.checkPushUp(pushUpSensorData.value)
                }
                "Situps" -> {
                    sitUpUtil.checkRepetition(sitUpSensorData.value)
                }
                else -> {
                    squatUtil.checkSquat(squatSensorData.value)
                }
            }
    }

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

    fun switchWorkingOut() {
        workingOut = !workingOut
        if(workingOut) {
            startTime = System.currentTimeMillis()
        } else {
            timerRunning = false
        }
    }

    fun calculateKCalInside(workoutName: String, repetitions: Int, user: User): Int {
        val kCal:Double = when (workoutName) {
            "Squats" -> {repetitions/25.0 * user.weight * 0.0175 * 5.5} // oder 6 * user.weight * timeInHours
            "Pushups" -> {repetitions * user.weight * 0.2906} // 8 * user.weight * timeInHours
            else -> {repetitions * 0.5 * user.weight/150} // 8 * user.weight * timeInHours
        }
        return kCal.toInt()
    }

    fun calculateKCalOutside(workoutName: String, distance: Double, timeInHours: Int, user: User): Int {
        val kCal:Double = when (workoutName) {
            "Running" -> {6.5 * user.weight * timeInHours}
            "Biking" -> {7.0 * user.weight * timeInHours}
            else -> {3.6 * user.weight * timeInHours}
        }
        return kCal.toInt()
    }

    fun saveWorkout(result: InsideWorkout) {
        viewModelScope.launch {
            workoutRepository.insertInsideWorkout(result)
        }
    }

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

    fun getLatLngList(): List<LatLng> {
        val points = mutableListOf<LatLng>()
        for (track in trackList) {
            points.add(LatLng(track.lat, track.long))
        }
        return points
    }

    fun updateTracks() {
        val currentTime = System.currentTimeMillis()
        var lastTrackTime: Long = 0
        if (trackList.isNotEmpty()) {
            lastTrackTime = trackList.last().timestamp
        }
        val difference = (currentTime - lastTrackTime)

        if (difference >= 10000) {
            Log.d("asd", "ten")
            // create new Track and add it to track list
            viewModelScope.launch {
                val location = locationTracker.getLocation()

                if (location != null) {
                    Log.d("asd", "got location")
                    var newTrack = Track(
                        workoutId = 0,
                        lat = location.latitude,
                        long = location.longitude,
                        altitude = location.altitude,
                        timestamp = currentTime
                    )
                    trackList.add(newTrack)
                    Log.d("asd", newTrack.toString())
                }
                else { Log.d("asd", "no location") }
            }
        }
    }

    init {
        viewModelScope.launch {
            healthConnectRepository.loadExercises()
        }
        viewModelScope.launch {
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

}