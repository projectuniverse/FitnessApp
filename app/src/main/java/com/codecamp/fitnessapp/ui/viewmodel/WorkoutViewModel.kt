package com.codecamp.fitnessapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codecamp.fitnessapp.data.track.DefaultTrackRepository
import com.codecamp.fitnessapp.data.user.DefaultUserRepository
import com.codecamp.fitnessapp.data.workout.DefaultWorkoutRepository
import com.codecamp.fitnessapp.healthconnect.HealthConnectRepositoryInterface
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
import kotlinx.coroutines.launch
import java.lang.Math.*
import javax.inject.Inject
import kotlin.random.Random

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

    val distance: MutableLiveData<String> by lazy { MutableLiveData("0.00") }
    val pace: MutableLiveData<String> by lazy { MutableLiveData("00:00") }
    val paceKm: MutableLiveData<String> by lazy { MutableLiveData("00:00") }

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

    /*
    * returns the sorted sequence in which order the old Workouts need to be displayed
    * */
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
            "Squats" -> {repetitions/25.0 * user.weight * 0.0175 * 5.5} // oder 6 * user.weight * timeInHours
            "Pushups" -> {repetitions * user.weight * 0.2906} // 8 * user.weight * timeInHours
            else -> {repetitions * 0.5 * user.weight/150} // 8 * user.weight * timeInHours
        }
        return kCal.toInt()
    }

    /*
    * calculates the burned calories of the outside workouts
    * */
    fun calculateKCalOutside(workoutName: String, distance: Double, timeInHours: Int, user: User): Int {
        val kCal:Double = when (workoutName) {
            "Running" -> {6.5 * user.weight * timeInHours}
            "Biking" -> {7.0 * user.weight * timeInHours}
            else -> {3.6 * user.weight * timeInHours}
        }
        return kCal.toInt()
    }

    /*
    * saves an inside workout in the repository
    * */
    fun saveWorkout(result: InsideWorkout) {
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

    fun getLatLngList(): List<LatLng> {
        val points = mutableListOf<LatLng>()
        for (track in trackList) {
            points.add(LatLng(track.lat, track.long))
        }
        return points
    }

    fun updateTracks() {
        if (!workingOut) return

        val currentTime = System.currentTimeMillis()
        var lastTrackTime: Long = 0
        if (trackList.isNotEmpty()) {
            lastTrackTime = trackList.last().timestamp
        }
        val difference = (currentTime - lastTrackTime)

        if (difference >= 10000) {
            // create new Track and add it to track list
            viewModelScope.launch {
                val debugOn = true

                if (!debugOn) {
                    val location = locationTracker.getLocation()
                    if (location != null) {
                        var newTrack = Track(
                            workoutId = 0,
                            lat = location.latitude,
                            long = location.longitude,
                            altitude = location.altitude,
                            timestamp = currentTime
                        )
                        trackList.add(newTrack)
                        Log.d("trackgen", newTrack.toString())
                    }
                }
                else {
                    var lat = 51.3204621
                    var long = 9.4886897
                    var alt = 0.0

                    if (trackList.isNotEmpty() && trackList.last() != null) {
                        lat = trackList.last().lat + 0.0005 * (3 * Random.nextDouble() + 0.1)
                        long = trackList.last().long + 0.0005 * (3 * Random.nextDouble() + 0.1)
                    }

                    var newTrack = Track(
                        workoutId = 0,
                        lat = lat,
                        long = long,
                        altitude = alt,
                        timestamp = currentTime
                    )
                    trackList.add(newTrack)
                    Log.d("trackgen", newTrack.toString())
                }

                updateRunningData()
            }
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371 // radius of Earth in kilometers
        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a = kotlin.math.sin(latDistance / 2) * kotlin.math.sin(latDistance / 2) +
                kotlin.math.cos(toRadians(lat1)) * kotlin.math.cos(toRadians(lat2)) *
                kotlin.math.sin(lonDistance / 2) * kotlin.math.sin(lonDistance / 2)
        val c = 2 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))
        return r * c
    }

    private fun formatTime(doubleMinutes: Double): String {
        val minutes = doubleMinutes.toInt()
        val seconds = ((doubleMinutes - minutes) * 60).toInt()
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun updateRunningData() {
        if (trackList.isEmpty()) return

        var dis = 0.0
        var _paceKm = 0.0
        var lastTrack: Track? = null
        var elapsedTime = 0.0

        for (track in trackList) {
            if (lastTrack != null) {
                dis += calculateDistance(lastTrack.lat, lastTrack.long, track.lat, track.long)
            }
            lastTrack = track
        }

        if (trackList.size > 1) {
            elapsedTime = (trackList.last().timestamp.toDouble() - startTime) / (1000 * 60)
        }

        distance.postValue(String.format("%.2f", dis))
        pace.postValue(formatTime(elapsedTime / dis))

        if (dis > 1) {
            lastTrack = null
            var km = 0.0
            for (i in trackList.size - 1 downTo 0) {
                if (lastTrack != null) {
                    km += calculateDistance(lastTrack.lat, lastTrack.long, trackList[i].lat, trackList[i].long)

                    if (km > 1) {
                        var t = (trackList.last().timestamp.toDouble() - trackList[i].timestamp) / (1000 * 60)
                        _paceKm = t / km
                        break
                    }
                }
                lastTrack = trackList[i]
            }
            paceKm.postValue(formatTime(_paceKm))
        }
        else {
            paceKm.postValue(formatTime(elapsedTime / dis))
        }
    }


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

}