package com.codecamp.fitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.codecamp.fitnessapp.ui.FitnessApp
import com.codecamp.fitnessapp.ui.theme.FitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint

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
                Surface(color = MaterialTheme.colorScheme.surface) {
                    FitnessApp()
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