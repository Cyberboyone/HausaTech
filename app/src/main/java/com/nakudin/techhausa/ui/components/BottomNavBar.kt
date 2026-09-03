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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.ui.theme.HausaTechColors
import com.nakudin.techhausa.ui.theme.HausaTechSpacing

/**
 * Premium floating bottom navigation for the two top-level destinations.
 * Dark elevated pill with a coral soft-highlight active state and Hausa
 * labels. Only shown on Home/Progress (wired in MainActivity).
 */
@Composable
fun BottomNavBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = HausaTechSpacing.Lg, vertical = HausaTechSpacing.Md)
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = Color.Black.copy(alpha = 0.6f)
            )
            .clip(RoundedCornerShape(28.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(horizontal = HausaTechSpacing.Sm, vertical = HausaTechSpacing.Sm)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavItem(
                selected = currentRoute == "home",
                icon = Icons.Filled.Home,
                label = "Gida",
                onClick = { onNavigate("home") },
                modifier = Modifier.weight(1f)
            )
            NavItem(
                selected = currentRoute == "progress",
                icon = Icons.Filled.Insights,
                label = "Ci Gaba",
                onClick = { onNavigate("progress") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NavItem(
    selected: Boolean,
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (selected) HausaTechColors.Accent.copy(alpha = 0.16f)
                else Color.Transparent
            )
            .clickable(role = Role.Tab, onClick = onClick)
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            contentDescription = label,
            tint = if (selected) HausaTechColors.Accent
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(2.dp))
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) HausaTechColors.Accent
            else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
