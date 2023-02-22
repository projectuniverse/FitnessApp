package com.codecamp.fitnessapp.ui.compose.outside

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun backButton(name: String, func: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { func() }
    ) {
        Text(text = name)
    }
}