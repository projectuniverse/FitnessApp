package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopLabel(showSettings: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.width(90.dp))

        Text(text = "Fitness App", fontSize = 40.sp, color = MaterialTheme.colorScheme.onPrimary)

        Row(modifier = Modifier.width(128.dp),
            horizontalArrangement = Arrangement.End) {
            IconButton(onClick = showSettings) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "",
                    modifier = Modifier.size(128.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}