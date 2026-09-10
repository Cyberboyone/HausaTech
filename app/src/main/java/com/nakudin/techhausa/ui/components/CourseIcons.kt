package com.nakudin.techhausa.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

fun courseIconFor(key: String): ImageVector = when (key) {
    "shield" -> Icons.Filled.Shield
    "smart_toy" -> Icons.Filled.Psychology
    "cloud" -> Icons.Filled.Cloud
    "router" -> Icons.Filled.Wifi
    "code" -> Icons.Filled.Code
    "web" -> Icons.Filled.DesktopWindows
    "data" -> Icons.Filled.BarChart
    "mobile" -> Icons.Filled.PhoneAndroid
    else -> Icons.Filled.School
}

fun courseColorFor(courseId: String): Color = when (courseId) {
    "cybersecurity" -> Color(0xFF34D65C)
    "ai" -> Color(0xFF7C8CFF)
    "cloud_computing" -> Color(0xFF3FA8FF)
    "networking" -> Color(0xFFFF9F45)
    "programming" -> Color(0xFFC77CFF)
    "web_development" -> Color(0xFF33D6C0)
    "data_science" -> Color(0xFFFFD54A)
    "mobile_development" -> Color(0xFFB56CFF)
    else -> Color(0xFF8FA69A)
}
