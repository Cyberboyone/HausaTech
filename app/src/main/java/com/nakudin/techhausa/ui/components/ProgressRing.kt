package com.nakudin.techhausa.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.ui.theme.HausaTechColors

/**
 * Animated circular progress indicator with a gradient arc and a centered
 * percentage label — the signature visual of the dashboard and results.
 */
@Composable
fun ProgressRing(
    progress: Float,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 12.dp,
    gradient: Brush = HausaTechColors.AccentGradient,
    showPercentage: Boolean = true
) {
    var displayed by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(progress) { displayed = progress.coerceIn(0f, 1f) }
    val animated by animateFloatAsState(
        targetValue = displayed,
        animationSpec = tween(durationMillis = 1000),
        label = "progress-ring"
    )
    val trackColor = MaterialTheme.colorScheme.surfaceVariant
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                brush = gradient,
                startAngle = -90f,
                sweepAngle = 360f * animated,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        if (showPercentage) {
            Text(
                "${(animated * 100).toInt()}%",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

/**
 * Linear progress bar with smooth animated fill, rounded track, and a
 * gradient indicator.
 */
@Composable
fun AnimatedProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = HausaTechColors.Accent
) {
    var displayed by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(progress) { displayed = progress.coerceIn(0f, 1f) }
    val animated by animateFloatAsState(
        targetValue = displayed,
        animationSpec = tween(durationMillis = 800),
        label = "progress-bar"
    )
    LinearProgressIndicator(
        progress = { animated },
        modifier = modifier
            .height(8.dp)
            .clip(CircleShape),
        color = color,
        trackColor = MaterialTheme.colorScheme.surfaceVariant
    )
}
