package com.codecamp.fitnessapp.ui.screens.dashboard.weather

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.R

@Composable
fun WeatherWarning() {
    Card(
        border = BorderStroke(10.dp, MaterialTheme.colorScheme.error),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.error
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.Center) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = "Warning_Symbol",
                    modifier = Modifier.size(42.dp),
                )
            }

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = stringResource(R.string.weatherWarning),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}