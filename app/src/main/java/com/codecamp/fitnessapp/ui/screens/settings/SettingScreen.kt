package com.codecamp.fitnessapp.ui.screens.settings

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.User
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Composable
fun SettingScreen(
    settingViewModel: SettingsViewModel = hiltViewModel()
) {
    val user = settingViewModel.user.collectAsState(initial = null)
    val stringSettings = stringArrayResource(R.array.settings)

    var validValues = user.value != null

    var age = if (user.value?.age.toString() == "null") "" else user.value?.age.toString()
    var height = if (user.value?.height.toString() == "null") "" else user.value?.height.toString()
    var weight = if (user.value?.weight.toString() == "null") "" else user.value?.weight.toString()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        if(!validValues) {
            SettingWarning()
            Spacer(modifier = Modifier.height(40.dp))
        }
        PropertyRow(stringSettings[0], age) {
            age = it
            Log.d("HI", "$age $it")
            validValues = settingViewModel.isValidUser(age, height, weight)
        }
        PropertyRow(stringSettings[1], height) {
            height = it
            validValues = settingViewModel.isValidUser(age, height, weight)
        }
        PropertyRow(stringSettings[2], weight) {
            weight = it
            validValues = settingViewModel.isValidUser(age, height, weight)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier.fillMaxWidth(0.9f),
            onClick = { /*TODO Google Health Connect*/ }
        ) {
            Text(text = stringSettings[3])
        }
    }
}