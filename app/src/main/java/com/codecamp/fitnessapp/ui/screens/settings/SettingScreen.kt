package com.codecamp.fitnessapp.ui.screens.settings

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
import com.codecamp.fitnessapp.ui.screens.result.AltitudeResult
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SettingScreen(
    settingViewModel: SettingsViewModel = hiltViewModel()
) {
    val user = settingViewModel.user.collectAsState(initial = null)
    val stringSettings = stringArrayResource(R.array.settings)
    // If value is null, null.toString() returns "null" (not valid value)
    var age = MutableStateFlow(if (user.value?.age.toString() == "null") "" else user.value?.age.toString())
    var height = MutableStateFlow(if (user.value?.height.toString() == "null") "" else user.value?.height.toString())
    var weight = MutableStateFlow(if (user.value?.weight.toString() == "null") "" else user.value?.weight.toString())
    /*
     * Denotes whether all values are logical/valid.
     * Needs to be passed to SettingWarning so that only it can recompose and appear/disappear accordingly.
     * This way, SettingScreen (this composable) only recomposes when there are new valid
     * values in the database (not if the warning message recomposes).
     * This allows:
     * - 1. Age, height and weight to represent the user input (even if it is illogical)
     *   without being overwritten by the database values during a recompose.
     * - 2. Writes to the database when all 3 values (inputs from the user) are logical,
     *   causing a recompose of this composable
     */
    var isValidValues = MutableStateFlow(user.value != null)

    /*
     * Import the following when using remember/mutableStateOf:
     * import androidx.compose.runtime.getValue
     * import androidx.compose.runtime.setValue
     */

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, end = 30.dp)
    ) {
        SettingWarning(isValidValues)
        PropertyRow(stringSettings[0], age) {
            isValidValues.value = settingViewModel.isValidUser(age.value, height.value, weight.value)
        }
        PropertyRow(stringSettings[1], height) {
            isValidValues.value = settingViewModel.isValidUser(age.value, height.value, weight.value)
        }
        PropertyRow(stringSettings[2], weight) {
            isValidValues.value = settingViewModel.isValidUser(age.value, height.value, weight.value)
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