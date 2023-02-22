package com.codecamp.fitnessapp

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord
import androidx.health.connect.client.records.HeightRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.WeightRecord
import com.codecamp.fitnessapp.ui.compose.outside.outsideScreen
import com.codecamp.fitnessapp.ui.screens.WeatherCard
import com.codecamp.fitnessapp.ui.theme.FitnessAppTheme
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityTransition
import com.google.android.gms.location.ActivityTransitionRequest
import com.google.android.gms.location.DetectedActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // private val healthConnectViewModel: HealthConnectViewModel by viewModels()

    //@OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        GlobalScope.launch {
//            if (healthConnectViewModel.healthConnect.healthConnectClient != null) {
//                checkPermissionsAndRun(healthConnectViewModel.healthConnect.healthConnectClient!!)
//            }
//        }

        setContent {
            FitnessAppTheme {
                // A surface container using the 'background' color from the theme
                outsideScreen(outsideWorkoutType = "Walk")
                //WeatherCard(healthConnectViewModel)
            }
        }
    }

//    private val PERMISSIONS = setOf(
//        HealthPermission.getReadPermission(StepsRecord::class),
//        HealthPermission.getReadPermission(ActiveCaloriesBurnedRecord::class),
//        HealthPermission.getReadPermission(WeightRecord::class),
//        HealthPermission.getReadPermission(HeightRecord::class),
//
//    )
//
//    private val requestPermissionActivityContract = PermissionController.createRequestPermissionResultContract()
//
//    private val requestPermissions = registerForActivityResult(requestPermissionActivityContract) { granted ->
//        if (granted.containsAll(PERMISSIONS)) {
//            //permissions successfully granted
//        } else {
//            //lack of permissions
//        }
//    }
//
//    suspend fun checkPermissionsAndRun(healthConnectClient: HealthConnectClient) {
//        val granted = healthConnectClient.permissionController.getGrantedPermissions()
//        if (granted.containsAll(PERMISSIONS)) {
//            // Run
//        } else {
//            requestPermissions.launch(PERMISSIONS)
//        }
//    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FitnessAppTheme {
        Greeting("Android")
    }
}