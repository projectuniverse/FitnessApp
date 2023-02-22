package com.codecamp.fitnessapp.ui.screens.result

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultCard(name: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(80.dp),
        border = BorderStroke(4.dp, Color.Blue),
        backgroundColor = Color.LightGray
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(100.dp)) {
                Text(text = "$name:", fontSize = 25.sp)
            }

            Spacer(modifier = Modifier.height(3.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = value, fontSize = 25.sp)
            }
        }
    }
}