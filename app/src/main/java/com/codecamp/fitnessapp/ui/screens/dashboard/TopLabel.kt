package com.codecamp.fitnessapp.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopLabel(showSettings: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colors.primary),
        horizontalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.width(90.dp))

        Text(text = "Fitness App", fontSize = 40.sp, color = MaterialTheme.colors.onPrimary)

        Row(modifier = Modifier.width(128.dp),
            horizontalArrangement = Arrangement.End) {
            IconButton(onClick = showSettings) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "",
                    modifier = Modifier.size(128.dp),
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}