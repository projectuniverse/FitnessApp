package com.codecamp.fitnessapp.ui.viewmodel

import android.location.Location
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
import com.codecamp.fitnessapp.sensor.finall.GyroscopeRepository
import com.codecamp.fitnessapp.sensor.pushup.PushUpUtil
import com.codecamp.fitnessapp.sensor.situp.SitUpUtil
import com.codecamp.fitnessapp.sensor.squat.SquatUtil
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Math.*
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.random.Random

@HiltViewModel
class WorkoutViewModel
@Inject constructor(
    private val workoutRepository: DefaultWorkoutRepository,
    private val sitUpUtil: SitUpUtil,
    private val squatUtil: SquatUtil,
    private val pushUpUtil: PushUpUtil,
    private val userRepository: DefaultUserRepository,
    private val gyroscopeRepository: GyroscopeRepository,
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

    private val gyroscopeData = gyroscopeRepository.gyroscopeData

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
            "Squats" -> {repetitions/25.0 * user.weight * 0.0175 * 5.5}
            "Pushups" -> {repetitions * user.weight * 0.2906}
            else -> {repetitions * 0.5 * user.weight/150}
        }
        return kCal.toInt()
    }

    /*
    * calculates the burned calories of the outside workouts
    * */
    private fun calculateKCalOutside(workoutName: String, distance: Double, timeInHours: Double, user: User): Int {
        val kCal:Double = when (workoutName) {
            "Running" -> { (0.75 * distance * user.weight) + (timeInHours * 8 * user.weight)}
            "Biking" -> { (timeInHours * (distance / timeInHours) * 3.5 * user.weight) / 200 }
            else -> { (0.5 * distance * user.weight) }
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

    fun getLatLngList(): List<LatLng> {
        val points = mutableListOf<LatLng>()
        for (track in trackList) {
            points.add(LatLng(track.lat, track.long))
        }
        return points
    }

    fun getCurrentLocation(): LatLng {
        val location: Location? = null
        viewModelScope.launch {
            locationTracker.getLocation()
        }
        return if (location != null)
            LatLng(location.latitude, location.longitude)
        else
            LatLng(51.3204621, 9.4886897)
    }

    fun createNewTrack() {
        viewModelScope.launch {
            var newTrack: Track? = null
            val currentTime = System.currentTimeMillis()
            val debugOn = false

            if (!debugOn) {
                val location = locationTracker.getLocation()
                if (location != null) {
                    newTrack = Track(
                        workoutId = 0,
                        lat = location.latitude,
                        long = location.longitude,
                        altitude = location.altitude,
                        timestamp = currentTime
                    )
                }
            } else {
                var lat = 51.3204621
                var long = 9.4886897
                var alt = 0.0

                if (trackList.isNotEmpty() && trackList.last() != null) {
                    lat = trackList.last().lat + 0.0005 * (3 * Random.nextDouble() + 0.1)
                    long = trackList.last().long + 0.0005 * (3 * Random.nextDouble() + 0.1)
                }

                newTrack = Track(
                    workoutId = 0,
                    lat = lat,
                    long = long,
                    altitude = alt,
                    timestamp = currentTime
                )
            }
            trackList.add(newTrack!!)
            updateRunningData()
        }
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
            createNewTrack()
        }
    }

    private fun calculateTrackDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
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

    private fun calculateDistance(): Double {
        var dis = 0.0
        var lastTrack: Track? = null
        for (track in trackList) {
            if (lastTrack != null) {
                dis += calculateTrackDistance(lastTrack.lat, lastTrack.long, track.lat, track.long)
            }
            lastTrack = track
        }
        return dis
    }

    private fun calculateTimeForLastKm(): Double {
        var lastTrack: Track? = null
        var km = 0.0
        var t = (trackList.last().timestamp.toDouble() - startTime) / (1000 * 60)
        for (i in trackList.size - 1 downTo 0) {
            if (lastTrack != null) {
                km += calculateTrackDistance(lastTrack.lat, lastTrack.long, trackList[i].lat, trackList[i].long)

                if (km > 1) {
                    val t = (trackList.last().timestamp.toDouble() - trackList[i].timestamp) / (1000 * 60)
                    break
                }
            }
            lastTrack = trackList[i]
        }

        return t / km
    }

    private fun updateRunningData() {
        if (trackList.size > 1) {
            val dis = calculateDistance() // distance in km
            distance.postValue(String.format("%.2f", dis))

            val elapsedTime = (trackList.last().timestamp.toDouble() - startTime) / (1000 * 60)
            pace.postValue(formatTime(elapsedTime / dis))

            val _paceKm = calculateTimeForLastKm()
            paceKm.postValue(formatTime(_paceKm))
        }
    }

    fun saveOutsideWorkout(result: OutsideWorkout) {
        viewModelScope.launch {
            val id = workoutRepository.insertOutsideWorkout(result)

            // TODO fix bug
            Log.d("asd", "Inserted ID is: $id")
            for (track in trackList) {
                trackRepository.insertTrack(track.copy(workoutId = id))
            }
            Log.d("asd", "asdasdasd")
        }
    }

    private fun kmToSteps(km: Double, workoutName: String): Int {
        val steps: Double = when (workoutName) {
            "Running" -> { km * 1315 }
            "Hiking" -> { km * 1750 }
            else -> { 0.0 }
        }
        return steps.toInt()
    }

    fun createOutsideWorkout(workoutName: String, user: User): OutsideWorkout {
        val endTime = trackList.last().timestamp
        val startTime = trackList.first().timestamp
        val elapsedTime = (endTime - startTime).toDouble() / (1000 * 60)

        var dis = calculateDistance()
        var steps = kmToSteps(dis, workoutName)


        val kcal = calculateKCalOutside(workoutName, dis, (elapsedTime / 60), user)

        val pace = elapsedTime / dis

        return OutsideWorkout(
            name = workoutName,
            pace = pace,
            steps = steps,
            distance = (dis * 100).roundToInt().toDouble() / 100,
            kcal = kcal,
            endTime = (trackList.last().timestamp / 1000).toInt(),
            startTime = (trackList.first().timestamp / 1000).toInt()
        )
    }

    fun resetRepetitions() {
        //set repetitions to 0 for UI reset
        pushUpUtil.repetitions.value = 0
        sitUpUtil.repetitions.value = 0
        squatUtil.repetitions.value = 0
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