package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NearMe
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import com.codecamp.fitnessapp.R
import com.codecamp.fitnessapp.model.InsideWorkout
import com.codecamp.fitnessapp.model.OutsideWorkout

@Composable
fun StartButton(
    startNewInside: (insideWorkout: InsideWorkout) -> Unit,
    startNewOutside: (outsideWorkout: OutsideWorkout) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    val outsideActivities = stringArrayResource(R.array.outsideActivities)
    val insideActivities = stringArrayResource(R.array.insideActivities)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary)
        ) {
            outsideActivities.forEachIndexed { index, name ->
                DropdownMenuItem(
                    modifier = Modifier.height(40.dp),
                    onClick = {
                        startNewOutside(
                            OutsideWorkout(
                                0,
                                name,
                                0.0,
                                0,
                                0.0,
                                0,
                                0,
                                0
                            )
                        ) //TODO wie erstellen
                        selectedIndex = index
                        expanded = false
                    }) {
                    Text(text = name)
                }
            }
            insideActivities.forEachIndexed { index, name ->
                DropdownMenuItem(onClick = {
                    startNewInside(InsideWorkout(0, name, 0, 0, 0, 0))//TODO hier auch
                    selectedIndex = index + 3
                    expanded = false
                }) {
                    Text(text = name, color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }

        ExtendedFloatingActionButton(
            text = {
                Text(text = "Start Workout", color = Color.White)
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.NearMe,
                    contentDescription = "",
                    tint = Color.White,
                )
            },
            onClick = { expanded = !expanded },
        )


    }


}