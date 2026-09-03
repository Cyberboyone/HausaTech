package com.nakudin.techhausa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.data.CourseRepository
import com.nakudin.techhausa.data.ProgressStore
import com.nakudin.techhausa.ui.components.AdBanner
import com.nakudin.techhausa.ui.components.courseColorFor

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
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Koma baya")
                    }
                }
            )
        },
        bottomBar = { AdBanner() }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            course?.levels?.forEach { level ->
                val completed = levelProgress[level.level] ?: 0
                val total = level.lessons.size
                val progress = if (total > 0) completed.toFloat() / total else 0f

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onOpenLevel(level.level) },
                    shape = MaterialTheme.shapes.large
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(level.level, style = MaterialTheme.typography.titleLarge)
                            Spacer(Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                LinearProgressIndicator(
                                    progress = { progress },
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(6.dp)
                                        .clip(CircleShape),
                                    color = color,
                                    trackColor = color.copy(alpha = 0.15f)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "$completed/$total",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Spacer(Modifier.width(8.dp))
                        Icon(Icons.Filled.ChevronRight, contentDescription = null)
                    }
                }
            }
        }
    }
}
