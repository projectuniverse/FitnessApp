package com.codecamp.fitnessapp.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import com.codecamp.fitnessapp.ui.screens.result.AltitudeResult
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
                    startNewInside = { newInside ->
                        insideWorkout = newInside
                        navController.navigate(AppScreen.Inside.name)
                        title = AppScreen.Inside.name},
                    startNewOutside = { newOutside ->
                        outsideWorkout = newOutside
                        navController.navigate(AppScreen.Outside.name)
                        title = AppScreen.Outside.name
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
                        navController.navigate(AppScreen.Result.name)},
                    showOldOutside = { oldOutside ->
                        outsideWorkout = oldOutside
                        navController.navigate(AppScreen.Result.name)}
                )
            }

            composable(route = AppScreen.Settings.name) {
                SettingScreen()
            }

            composable(route = AppScreen.Inside.name) {
                outsideWorkout = null
                title = insideWorkout!!.name
                InsideScreen(
                    insideWorkout!!,
                    stopWorkout = { newInside ->
                    insideWorkout = newInside
                    navController.navigate(AppScreen.Result.name)}
                )
            }

            composable(route = AppScreen.Outside.name) {
                insideWorkout = null
                title = outsideWorkout!!.name

            }

            composable(route = AppScreen.Result.name) {
                val workoutName = outsideWorkout?.name ?: insideWorkout?.name
                title = navController.currentDestination?.route.toString() + ": " + workoutName
                ResultScreen(
                    insideWorkout = insideWorkout,
                    outsideWorkout = outsideWorkout
                )
            }
        }
    }
}