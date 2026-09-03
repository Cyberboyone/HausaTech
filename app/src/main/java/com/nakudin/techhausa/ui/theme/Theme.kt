package com.nakudin.techhausa.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// Warm, modern "EdTech" palette — coral accent on soft cream, matching the
// reference design's warmth while keeping each course's own brand color
// (used in CourseIcons / diagram generation) as a secondary accent layer.

private val Coral = Color(0xFFFF6B47)
private val PeachContainer = Color(0xFFFFE4D9)
private val PeachOnContainer = Color(0xFF7A2E12)
private val Charcoal = Color(0xFF2D3142)
private val Cream = Color(0xFFFBF8F6)
private val CreamSurfaceVariant = Color(0xFFF3EEEA)
private val WarmGrey = Color(0xFF6B6862)

private val LightColorScheme = lightColorScheme(
    primary = Coral,
    onPrimary = Color.White,
    primaryContainer = PeachContainer,
    onPrimaryContainer = PeachOnContainer,
    secondary = Charcoal,
    onSecondary = Color.White,
    background = Cream,
    onBackground = Charcoal,
    surface = Color.White,
    onSurface = Charcoal,
    surfaceVariant = CreamSurfaceVariant,
    onSurfaceVariant = WarmGrey,
    tertiary = Color(0xFF3F4E9E),
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF8A6B),
    onPrimary = Color(0xFF3D1300),
    primaryContainer = Color(0xFF5C2211),
    onPrimaryContainer = PeachContainer,
    secondary = Color(0xFFC5C6D8),
    background = Color(0xFF17161A),
    onBackground = Color(0xFFEDEAE6),
    surface = Color(0xFF201F23),
    onSurface = Color(0xFFEDEAE6),
    surfaceVariant = Color(0xFF2C2A2F),
    onSurfaceVariant = Color(0xFFB8B4AE),
    tertiary = Color(0xFFB8C4FF),
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(22.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

@Composable
fun TechHausaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Off by default: this app's warm palette is an intentional brand choice,
    // not something wallpaper-derived Material You colors should override.
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        shapes = AppShapes,
        content = content
    )
}
