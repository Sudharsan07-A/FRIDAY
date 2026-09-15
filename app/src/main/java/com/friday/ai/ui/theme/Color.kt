package com.friday.ai.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val FridayDarkColorScheme = darkColorScheme(
    primary = Color(0xFF00D9FF),           // Cyan/Arc reactor blue
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFF0099CC),  // Darker blue
    onPrimaryContainer = Color(0xFF00D9FF),
    secondary = Color(0xFFFF6B35),         // Orange/repulsor glow
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFFCC5500), // Darker orange
    onSecondaryContainer = Color(0xFFFF6B35),
    tertiary = Color(0xFF00FF88),          // Neon green accent
    onTertiary = Color(0xFF000000),
    error = Color(0xFFFF6B6B),
    onError = Color(0xFF000000),
    background = Color(0xFF0A0E27),        // Deep space black
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF121829),           // Dark surface
    onSurface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFF1A1F3A),    // Slightly lighter surface
    onSurfaceVariant = Color(0xFFB0B0B0)
)

@Composable
fun FridayTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = FridayDarkColorScheme,
        typography = FridayTypography,
        content = content
    )
}

object FridayColors {
    val ArcReactorBlue = Color(0xFF00D9FF)
    val RepulsorOrange = Color(0xFFFF6B35)
    val NeonGreen = Color(0xFF00FF88)
    val DeepBlack = Color(0xFF0A0E27)
    val SurfaceDark = Color(0xFF121829)
    val SurfaceLight = Color(0xFF1A1F3A)
    val Glow = Color(0xFF00D9FF)
    val ErrorRed = Color(0xFFFF6B6B)
}
