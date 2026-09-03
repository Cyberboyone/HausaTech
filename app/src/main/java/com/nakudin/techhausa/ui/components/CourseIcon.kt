package com.nakudin.techhausa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Premium course icon container: rounded square with the course's brand
 * color as a gradient wash plus a soft glow, in consistent dimensions.
 */
@Composable
fun CourseIcon(
    icon: ImageVector,
    color: Color,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(18.dp),
                spotColor = color.copy(alpha = 0.45f)
            )
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(
                    listOf(color.copy(alpha = 0.85f), color.copy(alpha = 0.45f))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(size * 0.48f)
        )
    }
}
