package com.nakudin.techhausa.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ---------------------------------------------------------------------------
// HausaTech design system — premium dark learning-app theme.
// Coral/pink/magenta accents on near-black surfaces, large rounded cards,
// generous spacing. The app is dark-first by design (see spec section 18).
// ---------------------------------------------------------------------------

object HausaTechColors {
    val Background = Color(0xFF0A0A0F)
    val Surface = Color(0xFF141419)
    val SurfaceVariant = Color(0xFF1C1C24)
    val Elevated = Color(0xFF23232D)

    val OnBackground = Color(0xFFF5F2EC)
    val OnSurface = Color(0xFFF5F2EC)
    val Muted = Color(0xFFA8A4B5)

    /** Primary coral-pink accent. */
    val Accent = Color(0xFFFF6B81)
    val AccentDeep = Color(0xFFFF3D6E)
    val CoralOrange = Color(0xFFFF8A5C)
    val Magenta = Color(0xFFB45CFF)

    val Success = Color(0xFF34D399)
    val Warning = Color(0xFFFFB020)
    val Error = Color(0xFFFF5C5C)

    val Outline = Color(0xFF2B2B36)

    /** Hero gradient: deep plum to near-black with a coral glow. */
    val HeroGradient = Brush.linearGradient(
        listOf(Color(0xFF3A1A2E), Color(0xFF1B1220), Color(0xFF121218))
    )

    /** Primary CTA gradient: coral orange to pink to magenta. */
    val AccentGradient = Brush.horizontalGradient(
        listOf(CoralOrange, Accent, AccentDeep)
    )

    /** Subtle card sheen used on elevated surfaces. */
    val CardSheen = Brush.linearGradient(
        listOf(Color(0xFF1E1E28), Color(0xFF15151C))
    )
}

/** Gradient wash for a course card, built from the course's own brand color. */
fun courseGradient(color: Color): Brush = Brush.linearGradient(
    listOf(color.copy(alpha = 0.30f), color.copy(alpha = 0.08f), Color.Transparent)
)

object HausaTechSpacing {
    val Xs: Dp = 4.dp
    val Sm: Dp = 8.dp
    val Md: Dp = 12.dp
    val Lg: Dp = 16.dp
    val Xl: Dp = 24.dp
    val Xxl: Dp = 32.dp
}

private val DarkColorScheme = darkColorScheme(
    primary = HausaTechColors.Accent,
    onPrimary = Color(0xFF2B0A12),
    primaryContainer = Color(0xFF3A1F2B),
    onPrimaryContainer = Color(0xFFFFD9E2),
    secondary = HausaTechColors.Magenta,
    onSecondary = Color.White,
    tertiary = HausaTechColors.CoralOrange,
    background = HausaTechColors.Background,
    onBackground = HausaTechColors.OnBackground,
    surface = HausaTechColors.Surface,
    onSurface = HausaTechColors.OnSurface,
    surfaceVariant = HausaTechColors.SurfaceVariant,
    onSurfaceVariant = HausaTechColors.Muted,
    surfaceContainerLow = HausaTechColors.Surface,
    surfaceContainer = HausaTechColors.SurfaceVariant,
    surfaceContainerHigh = HausaTechColors.Elevated,
    outline = HausaTechColors.Outline,
    outlineVariant = Color(0xFF20202A),
    error = HausaTechColors.Error,
)

private val baseTypography = Typography()

private val AppTypography = Typography(
    displaySmall = baseTypography.displaySmall.copy(fontWeight = FontWeight.Bold),
    headlineLarge = baseTypography.headlineLarge.copy(fontWeight = FontWeight.Bold),
    headlineMedium = baseTypography.headlineMedium.copy(fontWeight = FontWeight.Bold),
    headlineSmall = baseTypography.headlineSmall.copy(fontWeight = FontWeight.Bold),
    titleLarge = baseTypography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
    titleMedium = baseTypography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
    titleSmall = baseTypography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
    labelLarge = baseTypography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp),
)

@Composable
fun TechHausaTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}
