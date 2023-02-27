package com.codecamp.fitnessapp.ui.screens.settings

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codecamp.fitnessapp.R

@Composable
fun SettingScreen(
    settingViewModel: SettingsViewModel = hiltViewModel()
) {
    val user = settingViewModel.user.collectAsState(initial = null)
    val stringSettings = stringArrayResource(R.array.settings)

    var validValues = user.value != null

    // If value is null, null.toString() returns "null" (not valid value)
    var age = user.value?.age.toString()
    var height = user.value?.height.toString()
    var weight = user.value?.weight.toString()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        if(!validValues) {
            SettingWarning()
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
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier.fillMaxWidth(0.9f),
            onClick = { /*TODO Google Health Connect*/ }
        ) {
            Text(
                text = stringSettings[3],
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}