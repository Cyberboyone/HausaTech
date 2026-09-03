package com.nakudin.techhausa.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Maps the "icon" string in a course's JSON to a Material icon. Add a case
 * here when a new course introduces a new icon key.
 */
fun courseIconFor(key: String): ImageVector = when (key) {
    "shield" -> Icons.Filled.Shield
    "smart_toy" -> Icons.Filled.SmartToy
    "cloud" -> Icons.Filled.Cloud
    "router" -> Icons.Filled.Router
    "code" -> Icons.Filled.Code
    "web" -> Icons.Filled.Language
    "data" -> Icons.Filled.BarChart
    "mobile" -> Icons.Filled.PhoneAndroid
    else -> Icons.Filled.School
}

/**
 * Each course's brand color, matching the exact RGB values used in
 * tools/generate_diagrams_v2.py (the COLORS dict) so a course's icon,
 * progress bar, and diagrams all agree with each other.
 */
fun courseColorFor(courseId: String): Color = when (courseId) {
    "ai" -> Color(0xFF6A5ACD)
    "cloud_computing" -> Color(0xFF1E90FF)
    "cybersecurity" -> Color(0xFFD32F2F)
    "data_science" -> Color(0xFF009688)
    "mobile_development" -> Color(0xFFE91E63)
    "networking" -> Color(0xFF388E3C)
    "programming" -> Color(0xFFFF8F00)
    "web_development" -> Color(0xFF039BE5)
    else -> Color(0xFF6B6862)
}
