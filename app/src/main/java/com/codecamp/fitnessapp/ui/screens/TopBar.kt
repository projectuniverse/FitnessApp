package com.codecamp.fitnessapp.ui.screens

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codecamp.fitnessapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    showSettings: () -> Unit,
    navigateBack: () -> Unit
) {
    val dashboard = stringResource(R.string.Dashboard)
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 35.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            if (title != dashboard) {
                IconButton(
                    modifier = Modifier.width(100.dp),
                    onClick = { navigateBack() }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
        },
        actions = {
            if (title == dashboard) {
                IconButton(onClick = showSettings) {
                    Icon(
                        Icons.Default.Settings,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}