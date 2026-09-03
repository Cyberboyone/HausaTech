package com.nakudin.techhausa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.data.CourseRepository
import com.nakudin.techhausa.data.LastAccessed
import com.nakudin.techhausa.data.ProgressStore
import com.nakudin.techhausa.ui.components.ContinueLearningCard
import com.nakudin.techhausa.ui.components.CourseProgressCard
import com.nakudin.techhausa.ui.components.courseColorFor
import com.nakudin.techhausa.ui.components.courseIconFor
import kotlinx.coroutines.flow.combine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenCourse: (String) -> Unit,
    onOpenLesson: (String, String) -> Unit
) {
    val context = LocalContext.current
    val courses = remember { CourseRepository.getCourseSummaries(context) }
    val progressStore = remember { ProgressStore(context) }

    var query by remember { mutableStateOf("") }
    var lastAccessed by remember { mutableStateOf<LastAccessed?>(null) }
    var courseProgress by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }

    LaunchedEffect(Unit) {
        progressStore.lastAccessedFlow().collect { lastAccessed = it }
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

    val searchResults = remember(query) {
        if (query.isBlank()) emptyList() else CourseRepository.searchLessons(context, query)
    }

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Column {
                    Text("Sannu! \uD83D\uDC4B", style = MaterialTheme.typography.headlineSmall)
                    Text(
                        "Menene za ka koya yau?",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Nemi darasi...") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    singleLine = true,
                    shape = MaterialTheme.shapes.extraLarge
                )
            }

            if (query.isNotBlank()) {
                if (searchResults.isEmpty()) {
                    item {
                        Text(
                            "Ba a sami darasi ba mai wannan suna",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                } else {
                    items(searchResults, key = { it.lesson.id }) { result ->
                        ElevatedCard(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onOpenLesson(result.courseId, result.lesson.id) }
                        ) {
                            Column(Modifier.padding(14.dp)) {
                                Text(result.lesson.title, style = MaterialTheme.typography.titleSmall)
                                Text(
                                    result.courseTitle,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            } else {
                val last = lastAccessed
                if (last != null) {
                    val course = remember(last.courseId) { CourseRepository.getCourse(context, last.courseId) }
                    val lesson = remember(last.courseId, last.lessonId) {
                        CourseRepository.getLesson(context, last.courseId, last.lessonId)
                    }
                    if (course != null && lesson != null) {
                        item {
                            val completed = courseProgress[last.courseId] ?: 0
                            val total = course.levels.sumOf { it.lessons.size }
                            ContinueLearningCard(
                                courseTitle = course.title,
                                lessonTitle = lesson.title,
                                progress = if (total > 0) completed.toFloat() / total else 0f,
                                onClick = { onOpenLesson(last.courseId, last.lessonId) }
                            )
                        }
                    }
                }

                item {
                    Text("Kwasoshi", style = MaterialTheme.typography.titleMedium)
                }

                item {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(courses, key = { it.id }) { course ->
                            val color = courseColorFor(course.id)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(72.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(CircleShape)
                                        .background(color.copy(alpha = 0.15f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    IconButton(onClick = { onOpenCourse(course.id) }) {
                                        Icon(courseIconFor(course.icon), contentDescription = course.title, tint = color)
                                    }
                                }
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    course.title,
                                    style = MaterialTheme.typography.labelSmall,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }

                items(courses, key = { "card-" + it.id }) { course ->
                    CourseProgressCard(
                        title = course.title,
                        icon = courseIconFor(course.icon),
                        courseColor = courseColorFor(course.id),
                        completedCount = courseProgress[course.id] ?: 0,
                        totalCount = course.totalLessons,
                        onClick = { onOpenCourse(course.id) }
                    )
                }

                item { Spacer(Modifier.height(8.dp)) }
            }
        }
    }
}
