package com.codecamp.fitnessapp.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.R
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SettingWarning(
    isValidValues: MutableStateFlow<Boolean>
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom =  20.dp)
    ) {
        var currentValue = isValidValues.collectAsState()
        // Only show warning message if the current inputs are illogical
        // Message disappears if MutableStateFlow isValidValues updates to true
        if (!currentValue.value) {
            Icon(
                Icons.Default.Warning,
                contentDescription = "",
                modifier = Modifier.size(50.dp),
                tint = Color.Red
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = stringResource(R.string.settingWarning),
                fontSize = 20.sp
            )
        }
    }
}