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
// HausaTech design system — premium dark green "tech education" theme.
// Bright lime/emerald green accents on near-black green-tinted surfaces,
// large rounded cards, thin hairline card borders, generous spacing.
// ---------------------------------------------------------------------------

object HausaTechColors {
    val Background = Color(0xFF0A130E)
    val Surface = Color(0xFF0F1D15)
    val SurfaceVariant = Color(0xFF152A1E)
    val Elevated = Color(0xFF1B3226)

    val OnBackground = Color(0xFFF3F7F4)
    val OnSurface = Color(0xFFF3F7F4)
    val Muted = Color(0xFF8FA69A)

    /** Primary brand green — buttons, active nav, progress fill. */
    val Accent = Color(0xFF34D65C)
    val AccentDeep = Color(0xFF1FAE46)
    val AccentBright = Color(0xFF5CF08A)

    val Success = Color(0xFF34D399)
    val Warning = Color(0xFFFFB020)
    val Error = Color(0xFFFF5C5C)

    val Outline = Color(0xFF1E3A28)

    /** Hero gradient: dark green with subtle radial glow. */
    val HeroGradient = Brush.linearGradient(
        listOf(Color(0xFF163826), Color(0xFF0D1F15), Color(0xFF0A130E))
    )

    /** Primary CTA gradient: deep green to bright green. */
    val AccentGradient = Brush.horizontalGradient(
        listOf(Color(0xFF1FAE46), Color(0xFF34D65C), Color(0xFF5CF08A))
    )

    /** Subtle card sheen used on elevated surfaces. */
    val CardSheen = Brush.linearGradient(
        listOf(Color(0xFF15291C), Color(0xFF0F1D15))
    )

    /** Radial glow for hero/header banners. */
    val GlowRadial = Brush.radialGradient(
        listOf(Color(0xFF1E4A30).copy(alpha = 0.55f), Color.Transparent)
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
    onPrimary = Color(0xFF0A130E),
    primaryContainer = Color(0xFF1B3226),
    onPrimaryContainer = Color(0xFFB8F5C9),
    secondary = HausaTechColors.AccentBright,
    onSecondary = Color.White,
    tertiary = HausaTechColors.AccentBright,
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
    outlineVariant = Color(0xFF142E20),
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
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(22.dp),
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
