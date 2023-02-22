package com.codecamp.fitnessapp.ui.compose.selectworkout

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun workoutButton(name: String) {
    Box(
        modifier = Modifier
            .clickable { Log.d("asd", "Hallo") }
    ) {
        Text(text = name)
    }
}