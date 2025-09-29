package com.example.practicas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AccentRed,
    onPrimary = MistWhite,
    secondary = AccentGold,
    onSecondary = MistWhite,
    tertiary = AccentBlue,
    background = MidnightBlue,
    onBackground = MistWhite,
    surface = SteelBlue,
    onSurface = MistWhite,
    surfaceVariant = IronGrey,
    onSurfaceVariant = SlateGrey
)

private val LightColorScheme = lightColorScheme(
    primary = AccentBlue,
    onPrimary = MistWhite,
    secondary = AccentRed,
    onSecondary = MistWhite,
    tertiary = AccentGold,
    background = MistWhite,
    onBackground = MidnightBlue,
    surface = Color(0xFFF5F7FF),
    onSurface = MidnightBlue,
    surfaceVariant = Color(0xFFE2E6F2),
    onSurfaceVariant = Color(0xFF465066)
)

@Composable
fun PracticasTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
