package com.codecamp.fitnessapp.ui.screens.outside

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*


@Composable
fun Map (
    latlngList: List<LatLng>
) {
    val zoom = 17f
    val start = LatLng(51.3204621,9.4886897)

//    val trackList = viewModel.trackList.collectAsState(initial = null).value

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(start, zoom)
    }


    // val locationSource: LocationSource =

    GoogleMap(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 5.dp),
        cameraPositionState = cameraPositionState,
//        locationSource = locationSource
    ) {
        if (latlngList != null && latlngList.isNotEmpty()) {
            Polyline(points = latlngList)
        }
    }
}