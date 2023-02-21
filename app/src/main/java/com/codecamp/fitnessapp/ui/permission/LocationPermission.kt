package com.codecamp.fitnessapp.ui.permission

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codecamp.fitnessapp.ui.theme.FitnessAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionScreen(permissionState: PermissionState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        elevation = 10.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Fitness App")
            Text(text = "Erlauben, auf Standort zuzugreifen?")
            Button(onClick = {
                permissionState.launchPermissionRequest()
            }) {
                Text(text = "Ja")
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview
@Composable
fun LocPermPreview() {
    FitnessAppTheme {
        val permissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION,
        onPermissionResult = {})
        LocationPermissionScreen(permissionState)
    }
}