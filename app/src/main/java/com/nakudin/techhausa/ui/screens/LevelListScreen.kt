package com.nakudin.techhausa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import com.nakudin.techhausa.data.CourseRepository
import com.nakudin.techhausa.data.ProgressStore
import com.nakudin.techhausa.ui.components.AdBanner
import com.nakudin.techhausa.ui.components.CourseIcon
import com.nakudin.techhausa.ui.components.Entrance
import com.nakudin.techhausa.ui.components.LevelCard
import com.nakudin.techhausa.ui.components.courseColorFor
import com.nakudin.techhausa.ui.components.courseIconFor
import com.nakudin.techhausa.ui.theme.HausaTechColors
import com.nakudin.techhausa.ui.theme.HausaTechSpacing

private data class LevelStyle(val subtitle: String, val icon: ImageVector, val accent: Color)

private fun levelStyle(level: String, courseColor: Color): LevelStyle = when (level) {
    "Beginner" -> LevelStyle(
        subtitle = "Foundation concepts — fara daga tushe",
        icon = Icons.Filled.School,
        accent = HausaTechColors.Success
    )
    "Intermediate" -> LevelStyle(
        subtitle = "Build your knowledge — ƙara ilimi",
        icon = Icons.AutoMirrored.Filled.TrendingUp,
        accent = HausaTechColors.Warning
    )
    else -> LevelStyle(
        subtitle = "Master the topic — zama gwani",
        icon = Icons.Filled.EmojiEvents,
        accent = courseColor
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelListScreen(courseId: String, onBack: () -> Unit, onOpenLevel: (String) -> Unit) {
    val context = LocalContext.current
    val course = remember(courseId) { CourseRepository.getCourse(context, courseId) }
    val progressStore = remember { ProgressStore(context) }
    val color = courseColorFor(courseId)

    var levelProgress by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }

    LaunchedEffect(course) {
        val levels = course?.levels.orEmpty()
        val flows = levels.map { lvl -> progressStore.completedCountFlow(lvl.lessons.map { it.id }) }
        if (flows.isNotEmpty()) {
            kotlinx.coroutines.flow.combine(flows) { counts ->
                levels.indices.associate { i -> levels[i].level to counts[i] }
            }.collect { levelProgress = it }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(course?.title ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Koma baya")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = { AdBanner() }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(HausaTechSpacing.Lg),
            verticalArrangement = Arrangement.spacedBy(HausaTechSpacing.Lg)
        ) {
            item {
                Entrance {
                    Column {
                        CourseIcon(
                            icon = courseIconFor(course?.icon ?: ""),
                            color = color,
                            contentDescription = course?.title
                        )
                        Spacer(Modifier.height(HausaTechSpacing.Md))
                        Text(
                            "Choose your level",
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            "Zaɓi matakin da ya dace da kai",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            val levels = course?.levels.orEmpty()
            itemsIndexed(levels, key = { _, level -> level.level }) { index, level ->
                Entrance(index = index) {
                    val style = levelStyle(level.level, color)
                    LevelCard(
                        title = level.level.uppercase(),
                        subtitle = style.subtitle,
                        icon = style.icon,
                        accent = style.accent,
                        completedCount = levelProgress[level.level] ?: 0,
                        totalCount = level.lessons.size,
                        onClick = { onOpenLevel(level.level) }
                    )
                }
            }
        }
    }
}
