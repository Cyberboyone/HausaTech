package com.nakudin.techhausa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.data.CourseRepository
import com.nakudin.techhausa.data.ProgressStore
import com.nakudin.techhausa.ui.components.CourseProgressCard
import com.nakudin.techhausa.ui.components.courseColorFor
import com.nakudin.techhausa.ui.components.courseIconFor
import kotlinx.coroutines.flow.combine

/**
 * The app-wide progress tab: total lessons completed across all 8 courses,
 * plus a per-course breakdown — the "how am I doing overall" view that
 * Home's per-course cards don't quite give you on their own.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(onOpenCourse: (String) -> Unit) {
    val context = LocalContext.current
    val courses = remember { CourseRepository.getCourseSummaries(context) }
    val progressStore = remember { ProgressStore(context) }
    val allLessonIds = remember { CourseRepository.getAllLessonIds(context) }

    var totalCompleted by remember { mutableIntStateOf(0) }
    var courseProgress by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }

    LaunchedEffect(allLessonIds) {
        progressStore.completedCountFlow(allLessonIds).collect { totalCompleted = it }
    }

    LaunchedEffect(courses) {
        val ids = courses.map { it.id to CourseRepository.getLessonIdsForCourse(context, it.id) }
        val flows = ids.map { (_, lessonIds) -> progressStore.completedCountFlow(lessonIds) }
        if (flows.isNotEmpty()) {
            combine(flows) { counts ->
                counts.indices.associate { i -> ids[i].first to counts[i] }
            }.collect { courseProgress = it }
        }
    }

    val totalLessons = allLessonIds.size
    val overallProgress = if (totalLessons > 0) totalCompleted.toFloat() / totalLessons else 0f

    Scaffold(
        topBar = { TopAppBar(title = { Text("Ci Gabanka") }) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.extraLarge,
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                progress = { overallProgress },
                                modifier = Modifier.size(120.dp),
                                strokeWidth = 10.dp,
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            )
                            Text(
                                "${(overallProgress * 100).toInt()}%",
                                style = MaterialTheme.typography.headlineMedium
                            )
                        }
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "$totalCompleted daga cikin $totalLessons darasi",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "a fadin dukkan kwasoshi",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Text("Kwas-kwas Daya-daya", style = MaterialTheme.typography.titleMedium)
            }

            items(courses, key = { it.id }) { course ->
                CourseProgressCard(
                    title = course.title,
                    icon = courseIconFor(course.icon),
                    courseColor = courseColorFor(course.id),
                    completedCount = courseProgress[course.id] ?: 0,
                    totalCount = course.totalLessons,
                    onClick = { onOpenCourse(course.id) }
                )
            }
        }
    }
}
