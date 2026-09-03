package com.nakudin.techhausa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.ui.theme.HausaTechColors
import com.nakudin.techhausa.ui.theme.HausaTechSpacing

/**
 * Premium hero card for Home: course + lesson, gradient progress ring with
 * percentage, motivational line, and a pill CTA — on a plum/coral gradient
 * with soft glowing accents.
 */
@Composable
fun ContinueLearningCard(
    courseTitle: String,
    lessonTitle: String,
    progress: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(HausaTechColors.HeroGradient)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(HausaTechSpacing.Xl)
    ) {
        // Soft glowing accent orbs.
        Box(
            modifier = Modifier
                .size(160.dp)
                .offset(x = 120.dp, y = (-60).dp)
                .clip(CircleShape)
                .background(HausaTechColors.Accent.copy(alpha = 0.22f))
                .align(Alignment.TopEnd)
        )
        Box(
            modifier = Modifier
                .size(120.dp)
                .offset(x = (-40).dp, y = 140.dp)
                .clip(CircleShape)
                .background(HausaTechColors.Magenta.copy(alpha = 0.16f))
                .align(Alignment.BottomStart)
        )

        Column {
            Text(
                "Continue Learning",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White.copy(alpha = 0.75f)
            )
            Spacer(Modifier.height(HausaTechSpacing.Sm))
            Text(
                courseTitle,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Text(
                lessonTitle,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.8f),
                maxLines = 2
            )
            Spacer(Modifier.height(HausaTechSpacing.Lg))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(HausaTechSpacing.Lg)
            ) {
                ProgressRing(
                    progress = progress,
                    modifier = Modifier.size(110.dp),
                    strokeWidth = 10.dp
                )
                Column(Modifier.weight(1f)) {
                    Text(
                        "You're doing great",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Spacer(Modifier.height(HausaTechSpacing.Xs))
                    Text(
                        "Ci gaba daga inda ka tsaya",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
            Spacer(Modifier.height(HausaTechSpacing.Lg))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(HausaTechColors.AccentGradient)
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Ci Gaba da Koyo",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                    Spacer(Modifier.width(HausaTechSpacing.Sm))
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}
