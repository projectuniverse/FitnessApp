package com.codecamp.fitnessapp.ui.screens.settings

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyRow(measurement: String, value: MutableStateFlow<String>, onChange: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
    ) {
        var currentValue = value.collectAsState()
        Text(
            text = measurement,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )
        TextField(
            value = currentValue.value,
            onValueChange = {
                value.value = it
                onChange(it)
                            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true
        )
    }
}