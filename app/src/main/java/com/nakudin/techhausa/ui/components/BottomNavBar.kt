package com.nakudin.techhausa.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Insights
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * The app's two top-level destinations. Course/level/lesson/quiz screens are
 * pushed on top of these without the bottom bar, so reading and quizzes get
 * the full screen — matching how most content apps hide chrome while you're
 * actually inside a piece of content.
 */
@Composable
fun BottomNavBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { onNavigate("home") },
            icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text("Gida") }
        )
        NavigationBarItem(
            selected = currentRoute == "progress",
            onClick = { onNavigate("progress") },
            icon = { Icon(Icons.Filled.Insights, contentDescription = null) },
            label = { Text("Ci Gaba") }
        )
    }
}
