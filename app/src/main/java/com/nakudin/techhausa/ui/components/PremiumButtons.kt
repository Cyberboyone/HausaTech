package com.nakudin.techhausa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.ui.theme.HausaTechColors

private val PillShape = RoundedCornerShape(20.dp)

/**
 * Primary call-to-action: full-width gradient pill with optional leading icon.
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(PillShape)
            .then(
                if (enabled) Modifier.background(HausaTechColors.AccentGradient)
                else Modifier.background(MaterialTheme.colorScheme.surfaceVariant)
            )
            .clickable(role = Role.Button, enabled = enabled, onClick = onClick)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.let {
                Icon(it, contentDescription = null, tint = Color.White)
                Spacer(Modifier.width(8.dp))
            }
            Text(
                text,
                style = MaterialTheme.typography.labelLarge,
                color = if (enabled) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Secondary action: dark translucent tonal pill with accent-colored content.
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(PillShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            icon?.let {
                Icon(it, contentDescription = null, tint = HausaTechColors.Accent)
                Spacer(Modifier.width(8.dp))
            }
            Text(
                text,
                style = MaterialTheme.typography.labelLarge,
                color = HausaTechColors.Accent
            )
        }
    }
}
