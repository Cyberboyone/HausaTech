package com.nakudin.techhausa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.ui.theme.HausaTechColors
import com.nakudin.techhausa.ui.theme.HausaTechSpacing

/**
 * Large overall-progress hero: gradient ring with percentage, completed /
 * total lessons, and an optional stats row underneath.
 */
@Composable
fun OverallProgressCard(
    completed: Int,
    total: Int,
    modifier: Modifier = Modifier,
    headline: String = "$completed / $total lessons completed",
    subline: String = "Ka kammala darasi $completed daga cikin $total",
    stats: @Composable (() -> Unit)? = null
) {
    val progress = if (total > 0) completed.toFloat() / total else 0f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(HausaTechColors.HeroGradient)
            .padding(HausaTechSpacing.Xl)
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(x = 140.dp, y = (-70).dp)
                .clip(CircleShape)
                .background(HausaTechColors.Accent.copy(alpha = 0.20f))
                .align(Alignment.TopEnd)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(HausaTechSpacing.Md)
        ) {
            ProgressRing(
                progress = progress,
                modifier = Modifier.size(150.dp),
                strokeWidth = 13.dp
            )
            Text(
                headline,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Text(
                subline,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f)
            )
            stats?.let {
                Spacer(Modifier.height(HausaTechSpacing.Xs))
                it()
            }
        }
    }
}

/** Small stat tile used inside progress heroes. */
@Composable
fun StatTile(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            value,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White.copy(alpha = 0.65f)
        )
    }
}

@Composable
fun StatRow(
    stats: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        stats.forEach { (value, label) ->
            StatTile(value = value, label = label, modifier = Modifier.weight(1f))
        }
    }
}
