package com.codecamp.fitnessapp.foregroundservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.codecamp.fitnessapp.BuildConfig
import com.codecamp.fitnessapp.foregroundservice.ActivityTransitionUtil.RECEIVER_ACTION
import com.google.android.gms.location.ActivityTransitionResult

class ActivityTransitionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.d("ACTIVITY_TRANSITION", "ON_RECEIVE")
/*        if (RECEIVER_ACTION == intent.action) {
            Log.d("ACTIVITY_TRANSITION", "Received an unsupported action")
            return
        }*/

        if (ActivityTransitionResult.hasResult(intent)) {
            val result = ActivityTransitionResult.extractResult(intent)
            for (event in result!!.transitionEvents) {
                val info =
                    "Transition: ${ActivityTransitionUtil.toActivityString(event.activityType)} " +
                            "- ${ActivityTransitionUtil.toTransitionType(event.transitionType)}"

                Log.d("ACTIVITY_TRANSITION", info)
                ActivityTransitionUtil.updateNotificationText(info)
            }
        }
    }
}