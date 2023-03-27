package com.codecamp.fitnessapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.app.ActivityCompat
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.*
import com.codecamp.fitnessapp.foregroundservice.ForegroundLocationService
import com.codecamp.fitnessapp.ui.FitnessApp
import com.codecamp.fitnessapp.ui.theme.FitnessAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // start Location tracking
        Intent(applicationContext, ForegroundLocationService::class.java).apply {
            action = ForegroundLocationService.ACTION_START
            startService(this)
        }

        GlobalScope.launch {
            if (HealthConnectClient.isApiSupported() && HealthConnectClient.isProviderAvailable(applicationContext)) {
                val client = HealthConnectClient.getOrCreate(applicationContext)
                checkPermissionsAndRun(client)
            }
        }

        ActivityCompat.requestPermissions(this,
            arrayOf(HealthPermission.getReadPermission(HeightRecord::class)),
            0
        )

        setContent {
            FitnessAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.surface) {
                    FitnessApp()
                }
            }
        }
    }

    private val PERMISSIONS = setOf(
        HealthPermission.getReadPermission(ExerciseSessionRecord::class),
        HealthPermission.getReadPermission(WeightRecord::class),
        HealthPermission.getReadPermission(HeightRecord::class)
    )

    private val requestPermissionActivityContract = PermissionController.createRequestPermissionResultContract()

    private val requestPermissions = registerForActivityResult(requestPermissionActivityContract) { granted ->
        if (granted.containsAll(PERMISSIONS)) {
            //permissions successfully granted
            Log.d("HEALTH_CONNECT", "permissions granted")
        } else {
            //lack of permissions
            Log.d("HEALTH_CONNECT", "permissions denied")
        }
    }

    suspend fun checkPermissionsAndRun(healthConnectClient: HealthConnectClient) {
        val granted = healthConnectClient.permissionController.getGrantedPermissions()
        if (granted.containsAll(PERMISSIONS)) {
            // already has permission form previous launch
            Log.d("HEALTH_CONNECT", "has permissions")
        } else {
            // has no permission yet
            Log.d("HEALTH_CONNECT", "no permissions -> start request")
            requestPermissions.launch(PERMISSIONS)
        }
    }
}