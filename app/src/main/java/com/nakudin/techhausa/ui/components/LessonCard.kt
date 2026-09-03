package com.nakudin.techhausa.ui.components

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.ui.theme.HausaTechColors
import com.nakudin.techhausa.ui.theme.HausaTechSpacing

/**
 * Premium lesson row-card: numbered badge, title + summary, completion
 * state, best-score chip, and chevron — with a clear completed treatment.
 */
@Composable
fun LessonCard(
    order: Int,
    title: String,
    summary: String,
    completed: Boolean,
    bestScore: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (completed) {
                HausaTechColors.Success.copy(alpha = 0.08f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (completed) {
            androidx.compose.foundation.BorderStroke(
                1.dp,
                HausaTechColors.Success.copy(alpha = 0.35f)
            )
        } else null
    ) {
        Row(
            modifier = Modifier.padding(HausaTechSpacing.Lg),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (completed) HausaTechColors.Success
                        else MaterialTheme.colorScheme.surfaceVariant
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (completed) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = "An kammala",
                        tint = Color.White
                    )
                } else {
                    Text(
                        "$order",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.width(HausaTechSpacing.Md))
            Column(Modifier.weight(1f)) {
                Text(
                    "Darasi $order: $title",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    summary,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
                if (completed && bestScore >= 0) {
                    Spacer(Modifier.height(HausaTechSpacing.Sm))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = HausaTechColors.Warning,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "Maki: $bestScore/5",
                            style = MaterialTheme.typography.labelSmall,
                            color = HausaTechColors.Success
                        )
                    }
                }
            }
            Spacer(Modifier.width(HausaTechSpacing.Sm))
            Icon(
                Icons.Filled.ChevronRight,
                contentDescription = "Buɗe darasi",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
