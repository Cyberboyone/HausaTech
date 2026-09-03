package com.nakudin.techhausa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.nakudin.techhausa.ui.components.LessonCard
import com.nakudin.techhausa.ui.components.courseColorFor
import kotlinx.coroutines.flow.combine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonListScreen(
    courseId: String,
    level: String,
    onBack: () -> Unit,
    onOpenLesson: (String) -> Unit
) {
    val context = LocalContext.current
    val course = remember(courseId) { CourseRepository.getCourse(context, courseId) }
    val lessons = remember(course, level) { course?.levels?.firstOrNull { it.level == level }?.lessons.orEmpty() }
    val progressStore = remember { ProgressStore(context) }
    val color = courseColorFor(courseId)

    var completedMap by remember { mutableStateOf<Map<String, Pair<Boolean, Int>>>(emptyMap()) }

    LaunchedEffect(lessons) {
        val flows = lessons.map { lesson ->
            progressStore.isCompleted(lesson.id).combine(progressStore.bestScore(lesson.id)) { done, score ->
                lesson.id to (done to score)
            }
        }
        if (flows.isNotEmpty()) {
            combine(flows) { pairs -> pairs.toMap() }.collect { completedMap = it }
        }
    }

    val completedCount = completedMap.values.count { it.first }
    val progress = if (lessons.isNotEmpty()) completedCount.toFloat() / lessons.size else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$level \u2014 ${course?.title ?: ""}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Koma baya")
                    }
                }
            )
        },
        bottomBar = { AdBanner() }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier
                            .weight(1f)
                            .height(8.dp)
                            .clip(CircleShape),
                        color = color,
                        trackColor = color.copy(alpha = 0.15f)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        "$completedCount/${lessons.size}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.height(4.dp))
            }

            items(lessons, key = { it.id }) { lesson ->
                val (done, score) = completedMap[lesson.id] ?: (false to -1)
                LessonCard(
                    order = lesson.order,
                    title = lesson.title,
                    summary = lesson.summary,
                    completed = done,
                    bestScore = score,
                    onClick = { onOpenLesson(lesson.id) }
                )
            }
        }
    }
}
