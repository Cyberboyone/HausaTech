package com.nakudin.techhausa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.ui.theme.HausaTechColors
import com.nakudin.techhausa.ui.theme.HausaTechSpacing

/**
 * Base premium card: dark tonal surface, 24dp corners, generous padding.
 * Pass [gradient] for hero/accent washes, [onClick] to make it tappable.
 */
@Composable
fun HausaTechCard(
    modifier: Modifier = Modifier,
    gradient: Brush? = null,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(24.dp)
    if (onClick != null) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            onClick = onClick
        ) {
            CardBody(gradient, shape, content)
        }
    } else {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(shape)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            CardBody(gradient, shape, content)
        }
    }
}

@Composable
private fun CardBody(
    gradient: Brush?,
    shape: RoundedCornerShape,
    content: @Composable () -> Unit
) {
    Box(
        modifier = if (gradient != null) {
            Modifier
                .fillMaxWidth()
                .clip(shape)
                .background(gradient)
        } else {
            Modifier.fillMaxWidth()
        }
    ) {
        Column(Modifier.padding(HausaTechSpacing.Xl)) {
            content()
        }
    }
}

/**
 * Section header: medium-weight title with an optional trailing action,
 * used above every content section for consistent hierarchy.
 */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f)
        )
        if (actionText != null && onActionClick != null) {
            TextButton(onClick = onActionClick) {
                Text(actionText, color = HausaTechColors.Accent)
            }
        }
    }
    Spacer(Modifier.height(HausaTechSpacing.Md))
}

/**
 * Small metadata chip (course name, level, lesson count) rendered on a
 * translucent dark pill.
 */
@Composable
fun MetaChip(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.10f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.85f)
        )
    }
}
