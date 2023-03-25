package com.codecamp.fitnessapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.R

/*
* The floatingActionButton that starts the workout chosen in the the dropdown menu
* */
@Composable
fun StartButton(
    title: String,
    startNewInside: (name: String) -> Unit,
    startNewOutside: (name: String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val text = stringArrayResource(R.array.WorkoutStats)[7]

    Column(
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(title == "Dashboard") {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                text = {
                    Text(text = text, fontSize = 20.sp)
                },
                shape = MaterialTheme.shapes.medium,
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "",
                    )
                },
                onClick = { expanded = !expanded },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )

            DropUpMenu(expanded, startNewInside, startNewOutside) { expanded = false }
        }
    }
}

@Composable
fun DropUpMenu(
    expanded: Boolean,
    startNewInside: (name: String) -> Unit,
    startNewOutside: (name: String) -> Unit,
    changeExpanded: () -> Unit
) {
    var selectedIndex by remember { mutableStateOf(0) }

    val outsideActivities = stringArrayResource(R.array.outsideActivities)
    val insideActivities = stringArrayResource(R.array.insideActivities)

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = changeExpanded,
        modifier = Modifier
            .fillMaxWidth(0.6f)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        outsideActivities.forEachIndexed { index, name ->
            val backgroundColor = if(index % 2 == 0) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            }
            DropdownMenuItem(
                text = {
                    Text(
                        text = name,
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                modifier = Modifier
                    .height(40.dp)
                    .background(backgroundColor),
                onClick = {
                    startNewOutside(name)
                    selectedIndex = index
                    changeExpanded()
                })
        }
        insideActivities.forEachIndexed { index, name ->
            val backgroundColor = if(index % 2 == 0) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.primary
            }
            DropdownMenuItem(
                text = {
                    Text(
                        text = name,
                        fontSize = 25.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                modifier = Modifier
                    .height(40.dp)
                    .background(backgroundColor),
                onClick = {
                    startNewInside(name)
                    selectedIndex = index + 3
                    changeExpanded()
                }
            )
        }
    }
}