package com.codecamp.fitnessapp.ui.screens.outside

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@Composable
fun Map (
    latlngList: List<LatLng>,
    start: LatLng
) {
    val zoom = 17f

    val cameraPositionState = CameraPositionState(position = CameraPosition(start, zoom, 0f, 0f))

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp),
        cameraPositionState = cameraPositionState
    ) {
        if (latlngList != null && latlngList.isNotEmpty()) {
            Polyline(points = latlngList)
        }
    }
}