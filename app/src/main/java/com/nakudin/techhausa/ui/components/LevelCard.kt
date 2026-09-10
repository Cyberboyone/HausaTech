package com.nakudin.techhausa.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.ui.theme.HausaTechColors
import com.nakudin.techhausa.ui.theme.HausaTechSpacing

@Composable
fun LevelCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accent: Color,
    completedCount: Int,
    totalCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f

    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, HausaTechColors.Outline)
    ) {
        Row(
            modifier = Modifier.padding(HausaTechSpacing.Xl),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(accent.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = accent, modifier = Modifier.size(24.dp))
            }
            Spacer(Modifier.width(HausaTechSpacing.Lg))
            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(HausaTechSpacing.Xs)
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = HausaTechColors.OnBackground
                )
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = HausaTechColors.Muted
                )
                Spacer(Modifier.height(HausaTechSpacing.Xs))
                AnimatedProgressBar(progress = progress, color = accent)
                Text(
                    "$completedCount / $totalCount lessons",
                    style = MaterialTheme.typography.labelMedium,
                    color = HausaTechColors.Muted
                )
            }
            Spacer(Modifier.width(HausaTechSpacing.Sm))
            Icon(
                Icons.Filled.ChevronRight,
                contentDescription = "Buɗe mataki",
                tint = HausaTechColors.Muted
            )
        }
    }
}
