package com.codecamp.fitnessapp.ui

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout
import com.codecamp.fitnessapp.ui.screens.dashboard.DashboardScreen
import com.codecamp.fitnessapp.ui.screens.dashboard.StartButton
import com.codecamp.fitnessapp.ui.screens.inside.InsideScreen
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
    var isVisible by remember { mutableStateOf(true) }
    val firstInit = false
    var insideWorkout: InsideWorkout = InsideWorkout(0, "",0,0,0,0)
    var outsideWorkout: OutsideWorkout = OutsideWorkout(0,"",0.0,0,0.0, 0, 0, 0)

    val firstscreen = if(firstInit) {
        AppScreen.Settings.name
    } else {
        AppScreen.Dashboard.name
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            StartButton(
                isVisible,
                startNewInside = { newInside ->
                    isVisible = false
                    insideWorkout = newInside
                    navController.navigate(AppScreen.Inside.name) },
                startNewOutside = { newOutside ->
                    isVisible = false
                    outsideWorkout = newOutside
                    navController.navigate(AppScreen.Outside.name)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        NavHost(
            navController = navController,
            startDestination = firstscreen
        ) {
            composable(route = AppScreen.Dashboard.name) {
                DashboardScreen(
                    showSettings = {
                        navController.navigate(AppScreen.Settings.name)
                        isVisible = false
                                   },
                    showOldInside = { oldInside ->
                        insideWorkout = oldInside
                        navController.navigate(AppScreen.Result.name) },
                    showOldOutside = { oldOutside ->
                        outsideWorkout = oldOutside
                        navController.navigate(AppScreen.Result.name) }
                )
            }

            composable(route = AppScreen.Settings.name) {
                SettingScreen(navigateBack = {
                    navController.popBackStack()
                    if(navController.currentDestination?.route == AppScreen.Dashboard.name) {
                        isVisible = true
                    }
                })
            }

            composable(route = AppScreen.Inside.name) {
                InsideScreen(
                    insideWorkout,
                    stopWorkout = { newInside ->
                    insideWorkout = newInside
                    navController.navigate(AppScreen.Result.name) },
                    navigateBack = {
                        navController.popBackStack()
                        if(navController.currentDestination?.route == AppScreen.Dashboard.name) {
                            isVisible = true
                        }
                    }
                )
            }

            composable(route = AppScreen.Outside.name) {

            }

            composable(route = AppScreen.Result.name) {
                ResultScreen(
                    insideWorkout = insideWorkout,
                    outsideWorkout = outsideWorkout,
                    backToDashboard = { navController.navigate(AppScreen.Dashboard.name) }
                )
            }
        }
    }
}