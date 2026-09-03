package com.nakudin.techhausa.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.data.CourseRepository
import com.nakudin.techhausa.data.ProgressStore
import com.nakudin.techhausa.ui.components.AdBanner
import com.nakudin.techhausa.ui.components.DiagramView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonScreen(
    courseId: String,
    lessonId: String,
    onBack: () -> Unit,
    onStartQuiz: (String) -> Unit
) {
    val context = LocalContext.current
    val lesson = remember(courseId, lessonId) { CourseRepository.getLesson(context, courseId, lessonId) }
    val progressStore = remember { ProgressStore(context) }

    if (lesson == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text("Ba a sami darasi ba")
        }
        return
    }

    LaunchedEffect(courseId, lessonId) {
        progressStore.recordLastAccessed(courseId, lessonId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Darasi ${lesson.order}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Koma baya")
                    }
                }
            )
        },
        bottomBar = { AdBanner() },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onStartQuiz(lesson.id) },
                text = { Text("Fara Jarabawa") },
                icon = {}
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Text(lesson.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))
            Text(lesson.content, style = MaterialTheme.typography.bodyLarge)

            lesson.diagram?.let { diagram ->
                Spacer(Modifier.height(16.dp))
                DiagramView(diagram)
            }

            Spacer(Modifier.height(80.dp)) // room so the FAB doesn't cover the last line
        }
    }
}
