package com.codecamp.fitnessapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.foregroundservice.ForegroundLocationService
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout
import com.codecamp.fitnessapp.ui.screens.StartButton
import com.codecamp.fitnessapp.ui.screens.TopBar
import com.codecamp.fitnessapp.ui.screens.dashboard.DashboardScreen
import com.codecamp.fitnessapp.ui.screens.inside.InsideScreen
import com.codecamp.fitnessapp.ui.screens.outside.OutsideScreen
import com.codecamp.fitnessapp.ui.screens.result.ResultScreen
import com.codecamp.fitnessapp.ui.screens.settings.SettingScreen

enum class AppScreen(@StringRes val title: Int) {
    Dashboard(title = R.string.Dashboard),
    Settings(title = R.string.Settings),
    Inside(title = R.string.Inside),
    Outside(title = R.string.Outside),
    Result(title = R.string.Result)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitnessApp(
    context: Context,
    navController: NavHostController = rememberNavController(),
    fitnessAppViewModel: FitnessAppViewModel = hiltViewModel(),
) {
    val firstInit = fitnessAppViewModel.firstInit.collectAsState(initial = null)
    val user = fitnessAppViewModel.user.collectAsState(initial = null)
    Log.d("TEST", "RECOMPOSE ${firstInit.value}")

    if (firstInit.value != null) {
        val _firstInit = if (user.value != null) {
            fitnessAppViewModel.setFirstInit(false)
            false
        } else {
            firstInit.value!!
        }
        val firstScreen = if (_firstInit) {
            AppScreen.Settings.name
        } else {
            AppScreen.Dashboard.name
        }
        var title by remember { mutableStateOf(firstScreen) }
        var new by remember { mutableStateOf(true) }
        var workoutName = ""
        var insideWorkout: InsideWorkout? = null
        var outsideWorkout: OutsideWorkout? = null

        /*
         * Costum onBackPressed function, that overrides the standard onBackPressed
         */
        val onBackPressedDispatcher =
            LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
        if (onBackPressedDispatcher != null) {
            val callback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (title.contains(AppScreen.Result.name)) {
                        navController.navigate(AppScreen.Dashboard.name)
                    } else if (title != AppScreen.Dashboard.name) {
                        navController.popBackStack()
                    }
                }
            }
            onBackPressedDispatcher.addCallback(LocalLifecycleOwner.current, callback)
            navController.setLifecycleOwner(LocalLifecycleOwner.current)
            navController.setOnBackPressedDispatcher(onBackPressedDispatcher)
        }

        /*
         * In this Scaffold sits the whole UI of the App
         * In every screen is a topbar and only in the dashboard a floatingActionButton
         * The navigation is implemented with a NavHost and NavController
         * The title of the topbar switches when entering a screen
         */
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    title,
                    showSettings = {
                        navController.navigate(AppScreen.Settings.name)
                        title = AppScreen.Settings.name
                    },
                    navigateBack = {
                        if (title.contains(AppScreen.Result.name)) {
                            navController.navigate(AppScreen.Dashboard.name)
                        } else {
                            navController.popBackStack()
                            //restart background activity tracking
                            Intent(context, ForegroundLocationService::class.java).apply {
                                action = ForegroundLocationService.ACTION_START_ACTIVITY_TRACKING
                                context.startService(this)
                            }
                        }
                    },
                    _firstInit
                )
            },
            floatingActionButton = {
                StartButton(
                    title,
                    startNewInside = { name ->
                        workoutName = name
                        navController.navigate(AppScreen.Inside.name)
                    },
                    startNewOutside = { name ->
                        workoutName = name
                        navController.navigate(AppScreen.Outside.name)
                        //stop background tracking while explicitly starting workout
                        Intent(context, ForegroundLocationService::class.java).apply {
                            action = ForegroundLocationService.ACTION_STOP_ACTIVITY_TRACKING
                            context.startService(this)
                        }
                    }
                )
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { innerPadding ->
            NavHost(
                // This line is necessary for the top bar to not overlay the content below it
                // See: https://www.youtube.com/watch?v=hQJpd78RUVg
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                navController = navController,
                startDestination = firstScreen
            ) {
                composable(route = AppScreen.Dashboard.name) {
                    title = navController.currentDestination?.route.toString()
                    DashboardScreen(
                        showOldInside = { oldInside ->
                            insideWorkout = oldInside
                            outsideWorkout = null
                            workoutName = oldInside.name
                            new = false
                            navController.navigate(AppScreen.Result.name)
                        },
                        showOldOutside = { oldOutside ->
                            outsideWorkout = oldOutside
                            insideWorkout = null
                            workoutName = oldOutside.name
                            new = false
                            navController.navigate(AppScreen.Result.name)
                        }
                    )
                }

                composable(route = AppScreen.Settings.name) {
                    SettingScreen(_firstInit)
                }

                composable(route = AppScreen.Inside.name) {
                    outsideWorkout = null
                    title = workoutName
                    InsideScreen(
                        title,
                        stopWorkout = { newInside ->
                            new = true
                            insideWorkout = newInside
                            navController.navigate(AppScreen.Result.name)
                        }
                    )
                }

                composable(route = AppScreen.Outside.name) {
                    insideWorkout = null
                    title = workoutName
                    OutsideScreen(
                        title,
                        stopWorkout = { newOutside ->
                            new = true
                            outsideWorkout = newOutside
                            navController.navigate(AppScreen.Result.name)
                            //when workout stops, restart automatic activity tracking
                            Intent(context, ForegroundLocationService::class.java).apply {
                                action = ForegroundLocationService.ACTION_START_ACTIVITY_TRACKING
                                context.startService(this)
                            }
                        }
                    )
                }

                composable(route = AppScreen.Result.name) {
                    title = navController.currentDestination?.route.toString() + ": " + workoutName
                    ResultScreen(
                        insideWorkout = insideWorkout,
                        outsideWorkout = outsideWorkout,
                        new = new
                    )
                }
            }
        }
    }
}