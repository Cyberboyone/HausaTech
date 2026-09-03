package com.nakudin.techhausa.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * Subtle entrance animation for cards and headers: fade + gentle rise,
 * staggered by [index] so lists feel alive without being distracting.
 */
@Composable
fun Entrance(
    index: Int = 0,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }
    val delay = (index * 60).coerceAtMost(300)
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(animationSpec = tween(durationMillis = 350, delayMillis = delay)) +
            slideInVertically(animationSpec = tween(durationMillis = 350, delayMillis = delay)) { it / 8 }
    ) {
        content()
    }
}
