package com.nakudin.techhausa.ui.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nakudin.techhausa.data.CourseRepository
import com.nakudin.techhausa.data.ProgressStore
import com.nakudin.techhausa.ui.components.AdBanner
import com.nakudin.techhausa.ui.components.DiagramView
import com.nakudin.techhausa.ui.components.Entrance
import com.nakudin.techhausa.ui.components.MetaChip
import com.nakudin.techhausa.ui.components.PrimaryButton
import com.nakudin.techhausa.ui.components.SupplementaryDiagramView
import com.nakudin.techhausa.ui.theme.HausaTechSpacing

private sealed class ContentSegment {
    data class TextBlock(val text: String) : ContentSegment()
    data class InlineImage(val asset: String) : ContentSegment()
}

private val IMAGE_TAG = Regex("\\[image:([^\\]]+)\\]")

private fun parseContentSegments(content: String): List<ContentSegment> {
    val segments = mutableListOf<ContentSegment>()
    var lastEnd = 0
    for (match in IMAGE_TAG.findAll(content)) {
        if (match.range.first > lastEnd) {
            val text = content.substring(lastEnd, match.range.first).trim()
            if (text.isNotBlank()) segments.add(ContentSegment.TextBlock(text))
        }
        segments.add(ContentSegment.InlineImage(match.groupValues[1]))
        lastEnd = match.range.last + 1
    }
    if (lastEnd < content.length) {
        val text = content.substring(lastEnd).trim()
        if (text.isNotBlank()) segments.add(ContentSegment.TextBlock(text))
    }
    return segments
}

/** Constrains readable content width on tablets while filling phones. */
@Composable
private fun CenteredMaxWidth(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.widthIn(max = 640.dp)) {
            content()
        }
    }
}

/**
 * Premium reading experience: dark background, large title, metadata chips,
 * rounded diagram container, comfortable Hausa body text, and the quiz CTA
 * as an end-of-content button (kept away from the ad banner below).
 */
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
    val course = remember(courseId) { CourseRepository.getCourse(context, courseId) }
    val level = remember(courseId, lessonId) { CourseRepository.getLevelForLesson(context, courseId, lessonId) }
    val progressStore = remember { ProgressStore(context) }

    if (lesson == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = { AdBanner() }
    ) { padding ->
        val segments = remember(lesson.content) { parseContentSegments(lesson.content) }
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(HausaTechSpacing.Lg),
            verticalArrangement = Arrangement.spacedBy(HausaTechSpacing.Lg)
        ) {
            item {
                Entrance {
                    CenteredMaxWidth {
                        Row {
                            MetaChip(course?.title ?: "")
                            Spacer(Modifier.width(HausaTechSpacing.Sm))
                            level?.let { MetaChip(it) }
                        }
                        Spacer(Modifier.height(HausaTechSpacing.Md))
                        Text(
                            lesson.title,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(Modifier.height(HausaTechSpacing.Sm))
                        Text(
                            lesson.summary,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            lesson.diagram?.let { diagram ->
                item {
                    Entrance(index = 1) {
                        CenteredMaxWidth {
                            DiagramView(diagram)
                        }
                    }
                }
            }

            lesson.supplementaryDiagrams?.forEachIndexed { idx, sDiagram ->
                item(key = "sd_$idx") {
                    CenteredMaxWidth {
                        SupplementaryDiagramView(sDiagram)
                    }
                }
            }

            segments.forEachIndexed { index, segment ->
                when (segment) {
                    is ContentSegment.TextBlock -> item(key = "text_$index") {
                        CenteredMaxWidth {
                            Text(
                                segment.text,
                                style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 28.sp),
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    is ContentSegment.InlineImage -> item(key = "img_$index") {
                        val ctx = LocalContext.current
                        val bitmap = remember(segment.asset, ctx) {
                            runCatching {
                                ctx.assets.open("images/${segment.asset}").use {
                                    BitmapFactory.decodeStream(it)
                                }
                            }.getOrNull()
                        }
                        bitmap?.let { bmp ->
                            CenteredMaxWidth {
                                Card(
                                    shape = RoundedCornerShape(20.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Image(
                                        bitmap = bmp.asImageBitmap(),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                CenteredMaxWidth {
                    Spacer(Modifier.height(HausaTechSpacing.Sm))
                    PrimaryButton(
                        text = "Fara Jarabawa",
                        icon = Icons.AutoMirrored.Filled.ArrowForward,
                        onClick = { onStartQuiz(lesson.id) }
                    )
                    // Breathing room so the CTA never sits against the ad banner.
                    Spacer(Modifier.height(HausaTechSpacing.Xl))
                }
            }
        }
    }
}
