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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
import com.nakudin.techhausa.ui.theme.HausaTechSpacing
import com.nakudin.techhausa.ui.theme.courseGradient

/**
 * Rich course card for the Home grid: icon with glow, title, level range,
 * lesson counts, animated progress bar with percentage, and a continue
 * action — washed in the course's own brand color.
 */
@Composable
fun CourseProgressCard(
    title: String,
    icon: ImageVector,
    courseColor: Color,
    completedCount: Int,
    totalCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f

    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(courseGradient(courseColor))
                .padding(HausaTechSpacing.Lg)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(HausaTechSpacing.Sm)) {
                CourseIcon(
                    icon = icon,
                    color = courseColor,
                    contentDescription = title
                )
                Spacer(Modifier.height(4.dp))
                Text(title, style = MaterialTheme.typography.titleLarge)
                Text(
                    "Beginner - Advanced",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "$completedCount / $totalCount lessons",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AnimatedProgressBar(
                        progress = progress,
                        color = courseColor,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "${(progress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelLarge,
                        color = courseColor,
                        modifier = Modifier.padding(start = HausaTechSpacing.Sm)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        "Continue",
                        style = MaterialTheme.typography.labelLarge,
                        color = courseColor
                    )
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = courseColor,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}
