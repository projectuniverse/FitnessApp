package com.codecamp.fitnessapp.foregroundservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.codecamp.fitnessapp.BuildConfig
import com.codecamp.fitnessapp.data.track.DefaultTrackRepository
import com.codecamp.fitnessapp.data.user.DefaultUserRepository
import com.codecamp.fitnessapp.data.workout.DefaultWorkoutRepository
import com.codecamp.fitnessapp.model.OutsideWorkout
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionResult
import com.google.android.gms.location.DetectedActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

class ActivityTransitionReceiver : BroadcastReceiver() {
    @Inject
    lateinit var workoutRepository: DefaultWorkoutRepository
    @Inject
    lateinit var trackRepository: DefaultTrackRepository
    @Inject
    lateinit var userRepository: DefaultUserRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (ActivityTransitionResult.hasResult(intent)) {
            val result = ActivityTransitionResult.extractResult(intent)
            for (event in result!!.transitionEvents) {
                val info =
                    "Transition: ${ActivityTransitionUtil.toActivityString(event.activityType)} " +
                            "- ${ActivityTransitionUtil.toTransitionType(event.transitionType)}"

                Log.d("ACTIVITY_TRANSITION", info)
                ActivityTransitionUtil.updateNotificationText(info)
                if (event.transitionType == ActivityTransition.ACTIVITY_TRANSITION_EXIT && event.activityType != DetectedActivity.STILL) {
                    ActivityTransitionUtil.isActiveWorkout = false
                    val endTime = System.currentTimeMillis()
                    val elapsedTime = (endTime - ActivityTransitionUtil.startTime).toDouble() / (1000 * 60) //min
                    val dis = ActivityTransitionUtil.calculateDistance()
                    val steps = ActivityTransitionUtil.kmToSteps(dis, ActivityTransitionUtil.name)
                    val pace = if (dis != 0.0) {
                        elapsedTime / dis
                    } else {
                        0.0
                    }
                    GlobalScope.launch {
                        val user = userRepository.user.first()
                        val kcal = ActivityTransitionUtil.calculateKCalOutside(ActivityTransitionUtil.name, dis, (elapsedTime / 60), user)
                        val outsideWorkout = OutsideWorkout(
                            name = ActivityTransitionUtil.name,
                            pace = pace,
                            steps = steps,
                            distance = (dis * 100).roundToInt().toDouble() / 100,
                            kcal = kcal,
                            startTime = (ActivityTransitionUtil.startTime / 1000).toInt(),
                            endTime = (endTime / 1000).toInt()
                        )
                        val id = async { workoutRepository.insertOutsideWorkout(outsideWorkout) }.await()
                        for (track in ActivityTransitionUtil.trackList) {
                            val tr = track.copy(workoutId = id)
                            trackRepository.insertTrack(tr)
                        }
                        ActivityTransitionUtil.trackList.clear()
                    }
                }
                if (event.transitionType == ActivityTransition.ACTIVITY_TRANSITION_ENTER) {
                    when(event.activityType) {
                        DetectedActivity.WALKING -> {
                            ActivityTransitionUtil.isActiveWorkout = true
                            ActivityTransitionUtil.name = "Hiking"
                            ActivityTransitionUtil.startTime = System.currentTimeMillis()
                         }
                        DetectedActivity.RUNNING -> {
                            ActivityTransitionUtil.isActiveWorkout = true
                            ActivityTransitionUtil.name = "Running"
                            ActivityTransitionUtil.startTime = System.currentTimeMillis()
                        }
                        DetectedActivity.ON_BICYCLE -> {
                            ActivityTransitionUtil.isActiveWorkout = true
                            ActivityTransitionUtil.name = "Biking"
                            ActivityTransitionUtil.startTime = System.currentTimeMillis()
                        }
                    }
                }
            }
        }
    }
}