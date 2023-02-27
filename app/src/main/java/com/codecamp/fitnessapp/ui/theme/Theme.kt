package com.codecamp.fitnessapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColorScheme(
    primary = Turquoise,
    secondary = TurquoiseAlt,
    onPrimary = BlueBlack,
    surface = WhiteBlue,
    surfaceVariant = ShinyBlue,
    onSurface = BlueBlack,
    error = Red,
    errorContainer = ErrorBlack,
    onSurfaceVariant = DarkBlue
)

@Composable
fun FitnessAppTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colorScheme = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}