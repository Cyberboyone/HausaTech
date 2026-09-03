package com.nakudin.techhausa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.data.CourseRepository
import com.nakudin.techhausa.data.LastAccessed
import com.nakudin.techhausa.data.ProgressStore
import com.nakudin.techhausa.ui.components.ContinueLearningCard
import com.nakudin.techhausa.ui.components.CourseProgressCard
import com.nakudin.techhausa.ui.components.Entrance
import com.nakudin.techhausa.ui.components.HausaSearchBar
import com.nakudin.techhausa.ui.components.SectionHeader
import com.nakudin.techhausa.ui.components.courseColorFor
import com.nakudin.techhausa.ui.components.courseIconFor
import com.nakudin.techhausa.ui.theme.HausaTechColors
import com.nakudin.techhausa.ui.theme.HausaTechSpacing
import kotlinx.coroutines.flow.combine

/**
 * Premium learning dashboard: greeting + hero progress card, a secondary
 * feature card, rounded search, and the 8 courses as rich gradient cards
 * in an adaptive grid (1 column on phones, 2 on tablets).
 */
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

    // Hoisted out of lazy scopes: remember() cannot run inside them.
    val last = lastAccessed
    val continueCourse = remember(last?.courseId) {
        last?.let { CourseRepository.getCourse(context, it.courseId) }
    }
    val continueLesson = remember(last?.courseId, last?.lessonId) {
        last?.let { CourseRepository.getLesson(context, it.courseId, it.lessonId) }
    }

    Scaffold { padding ->
        if (query.isNotBlank()) {
            SearchResultsList(
                results = searchResults,
                onOpenLesson = onOpenLesson,
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                header = {
                    HausaSearchBar(
                        query = query,
                        onQueryChange = { query = it },
                        modifier = Modifier.padding(bottom = HausaTechSpacing.Md)
                    )
                }
            )
        } else {
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
                                "Assalamu Alaikum \uD83D\uDC4B",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(HausaTechSpacing.Xs))
                            Text(
                                "Continue Your Learning",
                                style = MaterialTheme.typography.headlineLarge
                            )
                            Spacer(Modifier.height(HausaTechSpacing.Xs))
                            Text(
                                "Ci gaba daga inda ka tsaya — kowane darasi yana kusantar da kai ga burinka.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Entrance(index = 1) {
                        if (last != null && continueCourse != null && continueLesson != null) {
                            val total = continueCourse.levels.sumOf { it.lessons.size }
                            val done = courseProgress[last.courseId] ?: 0
                            ContinueLearningCard(
                                courseTitle = continueCourse.title,
                                lessonTitle = continueLesson.title,
                                progress = if (total > 0) done.toFloat() / total else 0f,
                                onClick = { onOpenLesson(last.courseId, last.lessonId) }
                            )
                        } else {
                            StartLearningCard(
                                onClick = {
                                    courses.firstOrNull()?.let { onOpenCourse(it.id) }
                                }
                            )
                        }
                    }
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Entrance(index = 2) {
                        LearnSomethingNewCard(
                            onClick = {
                                val target = courses.minByOrNull { courseProgress[it.id] ?: 0 }
                                target?.let { onOpenCourse(it.id) }
                            }
                        )
                    }
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    Entrance(index = 3) {
                        HausaSearchBar(
                            query = query,
                            onQueryChange = { query = it }
                        )
                    }
                }

                item(span = { GridItemSpan(maxLineSpan) }) {
                    SectionHeader(title = "Your Courses")
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
}

/** Empty-history state for the hero slot: invites the learner to begin. */
@Composable
private fun StartLearningCard(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(HausaTechColors.HeroGradient)
            .clickable(role = Role.Button, onClick = onClick)
            .padding(HausaTechSpacing.Xl)
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .offset(x = 120.dp, y = (-50).dp)
                .clip(CircleShape)
                .background(HausaTechColors.Accent.copy(alpha = 0.22f))
                .align(Alignment.TopEnd)
        )
        Column {
            Text(
                "Start Learning",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White.copy(alpha = 0.75f)
            )
            Spacer(Modifier.height(HausaTechSpacing.Sm))
            Text(
                "Fara tafiyar iliminka",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Text(
                "Zaɓi kwas ka fara darasi na farko — kyauta, cikin Hausa.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.75f)
            )
            Spacer(Modifier.height(HausaTechSpacing.Lg))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(HausaTechColors.AccentGradient)
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(HausaTechSpacing.Sm))
                    Text(
                        "Fara Koyo",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }
            }
        }
    }
}

/** Secondary feature card that routes to the least-started course. */
@Composable
private fun LearnSomethingNewCard(onClick: () -> Unit) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(HausaTechSpacing.Lg),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(HausaTechColors.Magenta.copy(alpha = 0.20f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.AutoAwesome,
                    contentDescription = null,
                    tint = HausaTechColors.Magenta
                )
            }
            Spacer(Modifier.width(HausaTechSpacing.Lg))
            Column(Modifier.weight(1f)) {
                Text("Learn Something New", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Explore courses and build your technology skills.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** Search results rendered as modern learning-app cards. */
@Composable
private fun SearchResultsList(
    results: List<com.nakudin.techhausa.data.CourseRepository.SearchResult>,
    onOpenLesson: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(HausaTechSpacing.Lg),
        verticalArrangement = Arrangement.spacedBy(HausaTechSpacing.Md)
    ) {
        item { header() }
        if (results.isEmpty()) {
            item {
                Text(
                    "Ba a sami darasi ba — gwada wata kalma.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = HausaTechSpacing.Lg)
                )
            }
        } else {
            items(results, key = { it.lesson.id }) { result ->
                val color = courseColorFor(result.courseId)
                Card(
                    onClick = { onOpenLesson(result.courseId, result.lesson.id) },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier.padding(HausaTechSpacing.Lg),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                        Spacer(Modifier.width(HausaTechSpacing.Md))
                        Column(Modifier.weight(1f)) {
                            Text(result.lesson.title, style = MaterialTheme.typography.titleMedium)
                            Text(
                                result.courseTitle,
                                style = MaterialTheme.typography.labelMedium,
                                color = color
                            )
                        }
                    }
                }
            }
        }
    }
}
