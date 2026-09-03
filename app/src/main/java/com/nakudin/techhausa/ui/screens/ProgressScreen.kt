package com.nakudin.techhausa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.data.CourseRepository
import com.nakudin.techhausa.data.ProgressStore
import com.nakudin.techhausa.ui.components.CourseProgressCard
import com.nakudin.techhausa.ui.components.Entrance
import com.nakudin.techhausa.ui.components.OverallProgressCard
import com.nakudin.techhausa.ui.components.SectionHeader
import com.nakudin.techhausa.ui.components.StatRow
import com.nakudin.techhausa.ui.components.courseColorFor
import com.nakudin.techhausa.ui.components.courseIconFor
import com.nakudin.techhausa.ui.theme.HausaTechSpacing
import kotlinx.coroutines.flow.combine

/**
 * Premium progress dashboard: greeting header, large overall ring card
 * with stats, then per-course cards in an adaptive grid.
 */
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
    val remaining = (totalLessons - totalCompleted).coerceAtLeast(0)

    Scaffold { padding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(HausaTechSpacing.Lg),
            verticalArrangement = Arrangement.spacedBy(HausaTechSpacing.Lg),
            horizontalArrangement = Arrangement.spacedBy(HausaTechSpacing.Lg)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Entrance {
                    Column {
                        Text(
                            "Your Progress",
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Spacer(Modifier.height(HausaTechSpacing.Xs))
                        Text(
                            "Track your learning journey",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Entrance(index = 1) {
                    OverallProgressCard(
                        completed = totalCompleted,
                        total = totalLessons,
                        stats = {
                            StatRow(
                                stats = listOf(
                                    "$totalCompleted" to "Completed",
                                    "$remaining" to "Remaining",
                                    "${courses.size}" to "Courses"
                                )
                            )
                        }
                    )
                }
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                SectionHeader(title = "Course Progress")
            }

            itemsIndexed(courses, key = { _, course -> course.id }) { index, course ->
                Entrance(index = index) {
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
}
