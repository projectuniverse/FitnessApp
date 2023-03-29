package com.codecamp.fitnessapp.foregroundservice

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.app.TaskInfo
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.codecamp.fitnessapp.BuildConfig
import com.codecamp.fitnessapp.model.OutsideWorkout
import com.codecamp.fitnessapp.model.Track
import com.codecamp.fitnessapp.model.User
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionRequest
import com.google.android.gms.location.DetectedActivity
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

object ActivityTransitionUtil {
    // Information about the current outside workout
    var isActiveWorkout = false
    var trackList = mutableListOf<Track>()
    lateinit var name: String
    var startTime: Int = 0

    var activityNotificationText = mutableStateOf("Null - Null")

    private val transitions = listOf(
        ActivityTransition.Builder()
            .setActivityType(DetectedActivity.STILL)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
            .build(),
        ActivityTransition.Builder()
            .setActivityType(DetectedActivity.WALKING)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
            .build(),
        ActivityTransition.Builder()
            .setActivityType(DetectedActivity.RUNNING)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
            .build(),
        ActivityTransition.Builder()
            .setActivityType(DetectedActivity.ON_BICYCLE)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
            .build(),
        ActivityTransition.Builder()
            .setActivityType(DetectedActivity.STILL)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
            .build(),
        ActivityTransition.Builder()
            .setActivityType(DetectedActivity.WALKING)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
            .build(),
        ActivityTransition.Builder()
            .setActivityType(DetectedActivity.RUNNING)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
            .build(),
        ActivityTransition.Builder()
            .setActivityType(DetectedActivity.ON_BICYCLE)
            .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
            .build()
    )

    val request = ActivityTransitionRequest(transitions)

    fun hasPermission(context: Context): Boolean {
        return (ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION)
                == PackageManager.PERMISSION_GRANTED)
    }

    fun toActivityString(activity: Int): String {
        return when(activity) {
            DetectedActivity.WALKING -> "WALKING"
            DetectedActivity.ON_BICYCLE -> "BICYCLE"
            DetectedActivity.RUNNING -> "RUNNING"
            else -> "UNKNOWN"
        }
    }

    fun toTransitionType(transitionType: Int): String {
        return when(transitionType) {
            ActivityTransition.ACTIVITY_TRANSITION_ENTER -> "ENTER"
            ActivityTransition.ACTIVITY_TRANSITION_EXIT -> "EXIT"
            else -> "UNKNOWN"
        }
    }

    fun updateNotificationText(info: String) {
        activityNotificationText.value = info
    }

    private fun calculateTrackDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371 // radius of Earth in kilometers
        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a = kotlin.math.sin(latDistance / 2) * kotlin.math.sin(latDistance / 2) +
                kotlin.math.cos(Math.toRadians(lat1)) * kotlin.math.cos(Math.toRadians(lat2)) *
                kotlin.math.sin(lonDistance / 2) * kotlin.math.sin(lonDistance / 2)
        val c = 2 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))
        return r * c
    }

    fun calculateDistance(): Double {
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

    fun kmToSteps(km: Double, workoutName: String): Int {
        val steps: Double = when (workoutName) {
            "Running" -> { km * 1315 }
            "Hiking" -> { km * 1750 }
            else -> { 0.0 }
        }
        return steps.toInt()
    }

    /*
    * calculates the burned calories of the outside workouts
    * */
    fun calculateKCalOutside(workoutName: String, distance: Double, timeInHours: Double, user: User): Int {
        val kCal:Double = when (workoutName) {
            "Running" -> { (0.75 * distance * user.weight) + (timeInHours * 8 * user.weight)}
            "Biking" -> { (timeInHours * (distance / timeInHours) * 3.5 * user.weight) / 200 }
            else -> { (0.5 * distance * user.weight) }
        }
        return kCal.toInt()
    }
}