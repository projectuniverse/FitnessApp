package com.codecamp.fitnessapp.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import com.codecamp.fitnessapp.HealthConnect
import com.codecamp.fitnessapp.HealthConnectViewModel
import com.codecamp.fitnessapp.ui.viewModels.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherCard(healthConnectViewModel: HealthConnectViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        elevation = 10.dp
    ) {
        val locationPermission = rememberPermissionState(
            permission = Manifest.permission.ACCESS_COARSE_LOCATION,
            onPermissionResult = { isGranted ->
                if (isGranted) {
                    //TODO: Load Weather in viewModel
                }
            }
        )

        if (locationPermission.status.isGranted) {
            WeatherDataDummy(healthConnectViewModel)
        } else {
            LocationPermissionCard(locationPermission)
        }
    }
}

@Composable
fun WeatherDataDummy(healthConnectViewModel: HealthConnectViewModel) {
    Text(text = "Weather Data")


    healthConnectViewModel.getStepsRecords()
    Column() {
        for (entry in healthConnectViewModel.stepRecordList) {
            Text(text = "${entry.count}")
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionCard(permissionState: PermissionState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Missing Permissions")
            Text(text = "Grant access to your location?")
        }
        Spacer(modifier = Modifier.padding(20.dp))
        Button(onClick = {
            permissionState.launchPermissionRequest()
        }) {
            Text(text = "Yes")
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ReadStepsPermissionCard(permissionState: PermissionState) {

}

/*@Preview
@Composable
fun WeatherPreview() {
    WeatherCard()
}*/