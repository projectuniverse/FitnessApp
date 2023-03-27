package com.codecamp.fitnessapp.foregroundservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.codecamp.fitnessapp.BuildConfig
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionResult
import com.google.android.gms.location.DetectedActivity

class ActivityTransitionReceiver : BroadcastReceiver() {

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
                    //TODO: workout vom typ activity type beenden
                }
                if (event.transitionType == ActivityTransition.ACTIVITY_TRANSITION_ENTER) {
                    when(event.activityType) {
                        DetectedActivity.WALKING -> {
                            //TODO: starte hiking workout //
                         }
                        DetectedActivity.RUNNING -> {
                            //TODO: starte running workout //
                        }
                        DetectedActivity.ON_BICYCLE -> {
                            //TODO: starte biking workout //
                        }
                    }
                }
            }
        }
    }
}