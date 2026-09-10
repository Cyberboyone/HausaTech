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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Icon
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

@Composable
fun OverallProgressCard(
    completed: Int,
    total: Int,
    modifier: Modifier = Modifier,
    headline: String = "$completed / $total Darussa Kammala",
    subline: String = "Jimlar Ci Gaba",
    stats: @Composable (() -> Unit)? = null
) {
    val progress = if (total > 0) completed.toFloat() / total else 0f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(HausaTechColors.HeroGradient)
            .padding(HausaTechSpacing.Xl)
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(x = 140.dp, y = (-70).dp)
                .clip(CircleShape)
                .background(HausaTechColors.GlowRadial)
                .align(Alignment.TopEnd)
        )

        Icon(
            Icons.Filled.EmojiEvents,
            contentDescription = null,
            tint = Color(0xFFFFD54A),
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.TopEnd)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(HausaTechSpacing.Md)
        ) {
            Text(
                subline,
                style = MaterialTheme.typography.labelLarge,
                color = HausaTechColors.Muted
            )
            Text(
                "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.displaySmall,
                color = HausaTechColors.Accent
            )
            ProgressRing(
                progress = progress,
                modifier = Modifier.size(120.dp),
                strokeWidth = 10.dp
            )
            Text(
                headline,
                style = MaterialTheme.typography.titleMedium,
                color = HausaTechColors.Muted
            )
            stats?.let {
                Spacer(Modifier.height(HausaTechSpacing.Xs))
                it()
            }
        }
    }
}

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
            color = HausaTechColors.Accent
        )
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = HausaTechColors.Muted
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
