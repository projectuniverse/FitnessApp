package com.codecamp.fitnessapp

import android.app.Application
import com.codecamp.fitnessapp.data.AppContainer
import com.codecamp.fitnessapp.data.AppDataContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FitnessApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}