package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.NearMe
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    text = {
                        Text(
                            text = name,
                            fontSize = 25.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
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
                    })
            }
            insideActivities.forEachIndexed { index, name ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = name,
                            fontSize = 25.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    onClick = {
                        startNewInside(InsideWorkout(0, name, 0, 0, 0, 0))
                        selectedIndex = index + 3
                        expanded = false
                    }
                ) //TODO hier auch
            }
        }
    }

    ExtendedFloatingActionButton(
        text = {
            Text(text = "Start Workout", color = Color.White)
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Menu,
                contentDescription = "",
                tint = Color.White,
            )
        },
        onClick = { expanded = !expanded },
    )
}