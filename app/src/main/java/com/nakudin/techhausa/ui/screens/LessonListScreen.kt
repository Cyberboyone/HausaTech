package com.nakudin.techhausa.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.nakudin.techhausa.data.CourseRepository
import com.nakudin.techhausa.data.ProgressStore
import com.nakudin.techhausa.ui.components.AdBanner
import com.nakudin.techhausa.ui.components.AnimatedProgressBar
import com.nakudin.techhausa.ui.components.CourseIcon
import com.nakudin.techhausa.ui.components.Entrance
import com.nakudin.techhausa.ui.components.HausaTechCard
import com.nakudin.techhausa.ui.components.LessonCard
import com.nakudin.techhausa.ui.components.MetaChip
import com.nakudin.techhausa.ui.components.courseColorFor
import com.nakudin.techhausa.ui.components.courseIconFor
import com.nakudin.techhausa.ui.theme.HausaTechSpacing
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
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(HausaTechSpacing.Lg),
            verticalArrangement = Arrangement.spacedBy(HausaTechSpacing.Md)
        ) {
            item {
                Entrance {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CourseIcon(
                                icon = courseIconFor(course?.icon ?: ""),
                                color = color,
                                contentDescription = course?.title
                            )
                            Spacer(Modifier.width(HausaTechSpacing.Lg))
                            Column(Modifier.weight(1f)) {
                                Text(
                                    level,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Spacer(Modifier.height(HausaTechSpacing.Xs))
                                Row {
                                    MetaChip(course?.title ?: "")
                                    Spacer(Modifier.width(HausaTechSpacing.Sm))
                                    MetaChip("${lessons.size} lessons")
                                }
                            }
                        }
                    }
                }
            }

            item {
                Entrance(index = 1) {
                    HausaTechCard {
                        Text(
                            "Your Progress",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(HausaTechSpacing.Xs))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AnimatedProgressBar(
                                progress = progress,
                                color = color,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                "${(progress * 100).toInt()}%",
                                style = MaterialTheme.typography.titleMedium,
                                color = color,
                                modifier = Modifier.padding(start = HausaTechSpacing.Sm)
                            )
                        }
                        Spacer(Modifier.height(HausaTechSpacing.Xs))
                        Text(
                            "$completedCount daga cikin ${lessons.size} darasi",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            itemsIndexed(lessons, key = { _, lesson -> lesson.id }) { index, lesson ->
                val (done, score) = completedMap[lesson.id] ?: (false to -1)
                LessonCard(
                    order = lesson.order,
                    title = lesson.title,
                    summary = lesson.summary,
                    completed = done,
                    bestScore = score,
                    onClick = { onOpenLesson(lesson.id) },
                    modifier = Modifier.fillMaxWidth()
                )

                // A single banner after every 5 lessons keeps the list monetized
                // without putting ads between every item or at the bottom as well.
                if ((index + 1) % 5 == 0 && index != lessons.lastIndex) {
                    Spacer(Modifier.height(HausaTechSpacing.Xs))
                    AdBanner(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}
