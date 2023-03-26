package com.codecamp.fitnessapp.location

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlinx.coroutines.suspendCancellableCoroutine

class LocationTrackerImplementation @Inject constructor(
    private val context: Context,
    private var fusedLocationClient: FusedLocationProviderClient
): LocationTrackerInterface {

    private fun hasPermissions(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsEnabled = locationManager.isProviderEnabled(Context.LOCATION_SERVICE) or
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val fineLocationPermission = ContextCompat.
        checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseLocationPermission = ContextCompat.
        checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (!gpsEnabled || !fineLocationPermission || !coarseLocationPermission) {
            return false
        }

        return true
    }

    @SuppressLint("MissingPermission")
    override suspend fun getLocation(): Location? {

        if (!hasPermissions()) {
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

    @SuppressLint("MissingPermission")
    override fun getLocationFlow(rate: Long): Flow<Location> {
        return callbackFlow {
            if (!hasPermissions()) {
                throw Exception("Missing location permissions")
            }

            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGpsEnabled && !isNetworkEnabled) {
                throw Exception("Gps and network disabled")
            }

            //val request = LocationRequest.create().setInterval(rate).setFastestInterval(rate)
            val request = LocationRequest.Builder(rate).build()

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    super.onLocationResult(p0)
                    p0.locations.lastOrNull()?.let { location ->
                        launch { send(location) }
                    }
                }
            }

            fusedLocationClient.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())

            awaitClose {
                fusedLocationClient.removeLocationUpdates(locationCallback)
            }
        }
    }
}