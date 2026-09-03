package com.nakudin.techhausa.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    ElevatedCard(modifier = modifier.fillMaxWidth(), onClick = onClick) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (completed) Icons.Filled.CheckCircle else Icons.Filled.PlayCircle,
                contentDescription = null,
                tint = if (completed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text("Darasi $order: $title", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(2.dp))
                Text(summary, style = MaterialTheme.typography.bodySmall, maxLines = 2)
                if (completed && bestScore >= 0) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Mafi Kyawun Maki: $bestScore/5",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
