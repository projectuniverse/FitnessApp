package com.codecamp.fitnessapp.ui.compose.selectworkout

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.codecamp.fitnessapp.ui.compose.outside.backButton
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@Composable
fun map() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val zoom = 17f
    val start = LatLng(51.3204621,9.4886897)

    val points = mutableListOf(start)

    points.add(LatLng(start.latitude + 0.002, start.longitude))
    points.add(LatLng(start.latitude, start.longitude + 0.002))
    points.add(LatLng(start.latitude - 0.002, start.longitude))
    points.add(LatLng(start.latitude, start.longitude - 0.002))

    var polylinePoints = remember { mutableStateOf(emptyList<LatLng>()) }
    polylinePoints.value = points


    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(start, zoom)
    }

    /*var move = {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(uni, zoom)
        marker.position = uni
    }*/

    Button(onClick = {
        val last = points.last()
        points.add(LatLng(last.latitude, last.longitude - 0.002))
        polylinePoints.value = points.toList()
        Log.d("asd", points.toString())
    }) {}


    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        //locationSource = ...
    ) {
        Polyline(points = polylinePoints.value)
    }


    
}