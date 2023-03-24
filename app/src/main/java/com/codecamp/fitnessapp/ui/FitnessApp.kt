package com.codecamp.fitnessapp.ui

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codecamp.fitnessapp.R
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
    navController: NavHostController = rememberNavController(),
) {
    val appName = stringResource(R.string.Dashboard)
    var title by remember { mutableStateOf(appName) }
    var workoutName = ""
    val firstInit = false
    var insideWorkout: InsideWorkout? = null
    var outsideWorkout: OutsideWorkout? = null

    // TODO Needs to be updated/changed.
    // TODO Use: https://developer.android.com/topic/libraries/architecture/datastore
    val firstscreen = if(firstInit) {
        AppScreen.Settings.name
    } else {
        AppScreen.Dashboard.name
    }

    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    if (onBackPressedDispatcher != null) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (title.contains(AppScreen.Result.name)) {
                    navController.navigate(AppScreen.Dashboard.name)
                } else if (title.contains(AppScreen.Dashboard.name)) {

                } else {
                    navController.popBackStack()
                }
            }
        }
        onBackPressedDispatcher.addCallback(LocalLifecycleOwner.current, callback)
        navController.setLifecycleOwner(LocalLifecycleOwner.current)
        navController.setOnBackPressedDispatcher(onBackPressedDispatcher)
    }


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
                    }
                }
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
            startDestination = firstscreen
        ) {
            composable(route = AppScreen.Dashboard.name) {
                title = navController.currentDestination?.route.toString()
                DashboardScreen(
                    showOldInside = { oldInside ->
                        insideWorkout = oldInside
                        outsideWorkout = null
                        workoutName = oldInside.name
                        navController.navigate(AppScreen.Result.name)},
                    showOldOutside = { oldOutside ->
                        outsideWorkout = oldOutside
                        insideWorkout = null
                        workoutName = oldOutside.name
                        navController.navigate(AppScreen.Result.name)}
                )
            }

            composable(route = AppScreen.Settings.name) {
                SettingScreen()
            }

            composable(route = AppScreen.Inside.name) {
                outsideWorkout = null
                title = workoutName
                InsideScreen(
                    title,
                    stopWorkout = { newInside ->
                    insideWorkout = newInside
                    navController.navigate(AppScreen.Result.name)}
                )
            }

            composable(route = AppScreen.Outside.name) {
                insideWorkout = null
                title = workoutName
                OutsideScreen(
                    title,
                    stopWorkout = { newOutside ->
                    outsideWorkout = newOutside
                    navController.navigate(AppScreen.Result.name)}
                )
            }

            composable(route = AppScreen.Result.name) {
                title = navController.currentDestination?.route.toString() + ": " + workoutName
                ResultScreen(
                    insideWorkout = insideWorkout,
                    outsideWorkout = outsideWorkout
                )
            }
        }
    }
}