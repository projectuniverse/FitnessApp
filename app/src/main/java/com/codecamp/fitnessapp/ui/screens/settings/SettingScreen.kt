package com.codecamp.fitnessapp.ui.screens.settings

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
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.User

@Composable
fun SettingScreen(
    navigateBack: () -> Boolean,
    //userViewmodel: UserViewModel = viewModel(factory = WeatherViewModel.Factory)
) {
    val stringSettings = stringArrayResource(R.array.settings)
    val currentUser = User(0, -5, 180, 80)

    var validValues by remember { mutableStateOf(false) }
    var userAge by remember { mutableStateOf(currentUser.age) }
    var userHeight by remember { mutableStateOf(currentUser.height) }
    var userWeight by remember { mutableStateOf(currentUser.weight) }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(modifier = Modifier.fillMaxWidth(0.9f)) {
            IconButton(
                onClick = {
                    if (validValues) {
                        //userViewmodel.changeUser(User(0, 0,0,0))//TODO wie erstellen
                        navigateBack()
                    }
                }
            ) {
                Icon(
                    Icons.Default.ArrowCircleLeft,
                    contentDescription = "",
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        SettingWarning(
            { validValues = true },
            { validValues = true },
            userAge,
            userHeight,
            userWeight
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            PropertyRow(stringSettings[0], userAge.toString()) { userAge = it }

            Spacer(modifier = Modifier.height(12.dp))

            PropertyRow(stringSettings[1], userHeight.toString()) { userHeight = it }

            Spacer(modifier = Modifier.height(12.dp))

            PropertyRow(stringSettings[2], userWeight.toString()) { userWeight = it }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                modifier = Modifier.fillMaxWidth(0.9f),
                onClick = { /*TODO Google Health Connect*/ }
            ) {
                Text(text = stringSettings[3])
            }
        }
    }
}