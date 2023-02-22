package com.codecamp.fitnessapp.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class LocationTrackerImplementation @Inject constructor(
    private var fusedLocationClient: FusedLocationProviderClient,
    private val app: Application
): LocationTrackerInterface {
    override suspend fun getLocation(): Location? {

        // check for necessary permissions
        val locationManager = app.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled = locationManager.isProviderEnabled(Context.LOCATION_SERVICE) or
                            locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val fineLocationPermission = ContextCompat.
            checkSelfPermission(app, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseLocationPermission = ContextCompat.
            checkSelfPermission(app, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (!gpsEnabled || !fineLocationPermission || !coarseLocationPermission) {
            return null
        }

        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        continuation.resume(result)
                    } else {
                        continuation.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    continuation.resume(it)
                }
                addOnCanceledListener {
                    continuation.cancel()
                }
            }
        }
    }
}