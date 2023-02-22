package com.codecamp.fitnessapp.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codecamp.fitnessapp.R

@Composable
fun SettingWarning(validValues: () -> Unit, inValidValues: () -> Unit, age: Int, height: Int, weight: Int) {

    if (!(age < 0 || age > 100
        || height < 50 || height > 300
        || weight < 20 || weight > 400)
    ) {
        validValues()
    } else {
        inValidValues()

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.width(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = "",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Red
                )
            }

            Spacer(modifier = Modifier.width(5.dp))
            Text(text = stringResource(R.string.settingWarning))
        }
    }
}