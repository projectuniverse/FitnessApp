package com.codecamp.fitnessapp.foregroundservice

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.location.LocationTrackerImplementation
import com.codecamp.fitnessapp.location.LocationTrackerInterface
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityRecognitionClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ForegroundLocationService: Service() {

    private lateinit var locationTracker: LocationTrackerInterface
    private lateinit var client: ActivityRecognitionClient
    private lateinit var receiver: ActivityTransitionReceiver

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        locationTracker = LocationTrackerImplementation(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
        client = ActivityRecognition.getClient(applicationContext)
        receiver = ActivityTransitionReceiver()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @SuppressLint("MissingPermission")
    private fun requestUpdates() {
        if (ActivityTransitionUtil.hasPermission(applicationContext)) {
            client.requestActivityTransitionUpdates(ActivityTransitionUtil.request, getPendingIntent()).addOnSuccessListener {
                Log.d("ACTIVITY_TRANSITION", "REQUEST SUCCESSFUL")
            }.addOnFailureListener {
                Log.d("ACTIVITY_TRANSITION", "REQUEST FAILURE")
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun removeUpdates() {
        if (ActivityTransitionUtil.hasPermission(applicationContext)) {
            client.removeActivityTransitionUpdates(getPendingIntent())
        }
    }

    private fun getPendingIntent(): PendingIntent {
        val newIntent = Intent(applicationContext, ActivityTransitionReceiver::class.java). apply {

        }
        return PendingIntent.getBroadcast(
            applicationContext,
            0,
            newIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    // Varaible isactiveworkout und wenn das der fall ist bei dem oneach track objekt erstellen
    private fun start() {
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Fitness App")
            .setContentText("Something went wrong. Please check permissions and start app new.")
            .setSmallIcon(R.drawable.fitnessappicon)
            .setOngoing(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        requestUpdates()
        locationTracker
            .getLocationFlow(10000L)
            .catch { e ->
                e.printStackTrace()
            }
            .onEach { location ->
                val lat = location.latitude
                val lon = location.longitude
                val updatedNotification = notification.setContentText("Fitness App is accessing your Location.")
                notificationManager.notify(1, updatedNotification.build())
            }
            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    private fun stop() {
        removeUpdates()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}