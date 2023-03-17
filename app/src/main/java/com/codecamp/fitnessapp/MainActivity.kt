package com.codecamp.fitnessapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.ActiveCaloriesBurnedRecord
import androidx.health.connect.client.records.HeightRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.WeightRecord
import com.codecamp.fitnessapp.foregroundservice.ForegroundLocationService
import com.codecamp.fitnessapp.healthconnect.HealthConnectViewModel
import com.codecamp.fitnessapp.sensor.TestScreen
import com.codecamp.fitnessapp.ui.FitnessApp
import com.codecamp.fitnessapp.ui.theme.FitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val healthConnectViewModel: HealthConnectViewModel by viewModels()
    //private val hcUsage = healthConnectViewModel.hcUsage

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // start Location tracking
        Intent(applicationContext, ForegroundLocationService::class.java).apply {
            action = ForegroundLocationService.ACTION_START
            startService(this)
        }

        GlobalScope.launch {
            if (healthConnectViewModel.healthConnect.healthConnectClient != null) {
                checkPermissionsAndRun(healthConnectViewModel.healthConnect.healthConnectClient!!)
            }
        }

        setContent {
            FitnessAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.surface) {
                    FitnessApp()

                    /*Button(onClick = {
                        Intent(applicationContext, ForegroundLocationService::class.java).apply {
                            action = ForegroundLocationService.ACTION_STOP
                            startService(this)
                        }
                    }) {
                        Text(text = "Stop Background Location Tracking")
                    }*/
                }
            }
        }
        /*
                outsideScreen(outsideWorkoutType = "Walk")
                //WeatherCard(healthConnectViewModel)
            }
        }

         */
    }

    private val PERMISSIONS = setOf(
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getReadPermission(ActiveCaloriesBurnedRecord::class),
        HealthPermission.getReadPermission(WeightRecord::class),
        HealthPermission.getReadPermission(HeightRecord::class),

    )

    private val requestPermissionActivityContract = PermissionController.createRequestPermissionResultContract()

    private val requestPermissions = registerForActivityResult(requestPermissionActivityContract) { granted ->
        if (granted.containsAll(PERMISSIONS)) {
            //permissions successfully granted
            healthConnectViewModel.updatePermissionGranted(false, true)
        } else {
            //lack of permissions
            healthConnectViewModel.updatePermissionGranted(false, false)
        }
    }

    suspend fun checkPermissionsAndRun(healthConnectClient: HealthConnectClient) {
        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        if (granted.containsAll(PERMISSIONS)) {
            // already has permission form previous launch
            //val usage = hcUsage.first().useHealthConnect
            healthConnectViewModel.updatePermissionGranted(false, true)
        } else {
            // has no permission yet
            requestPermissions.launch(PERMISSIONS)
        }
    }
}