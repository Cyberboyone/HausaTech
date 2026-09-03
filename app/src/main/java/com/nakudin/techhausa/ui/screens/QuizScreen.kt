package com.nakudin.techhausa.ui.screens

import android.app.Activity
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.ads.InterstitialManager
import com.nakudin.techhausa.ads.RewardedManager
import com.nakudin.techhausa.data.CourseRepository
import com.nakudin.techhausa.data.ProgressStore
import com.nakudin.techhausa.ui.components.AdBanner
import com.nakudin.techhausa.ui.components.AnimatedProgressBar
import com.nakudin.techhausa.ui.components.Entrance
import com.nakudin.techhausa.ui.components.OverallProgressCard
import com.nakudin.techhausa.ui.components.PrimaryButton
import com.nakudin.techhausa.ui.components.SecondaryButton
import com.nakudin.techhausa.ui.components.StatRow
import com.nakudin.techhausa.ui.theme.HausaTechColors
import com.nakudin.techhausa.ui.theme.HausaTechSpacing

/**
 * Premium focused quiz: question counter + progress, question in a rounded
 * card, large touch-target answers with selected/correct/incorrect states,
 * and a results hero with ring, stats, rewarded retry, and return actions.
 *
 * Quiz logic, interstitial/rewarded ads, and progress recording are
 * unchanged from the previous version — only presentation changed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    courseId: String,
    lessonId: String,
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    val lesson = remember(courseId, lessonId) { CourseRepository.getLesson(context, courseId, lessonId) }
    val progressStore = remember { ProgressStore(context) }

    if (lesson == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Ba a sami jarabawa ba")
        }
        return
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var answered by remember { mutableStateOf(false) }
    val answers = remember { mutableStateListOf<Boolean>() }
    var showResults by remember { mutableStateOf(false) }
    var interstitialShown by remember { mutableStateOf(false) }

    val activity = context as? Activity
    val interstitialManager = remember { InterstitialManager(context) }
    val rewardedManager = remember { RewardedManager(context) }

    fun resetQuiz() {
        currentIndex = 0
        selectedOption = null
        answered = false
        answers.clear()
        showResults = false
        interstitialShown = false
        interstitialManager.load()
        rewardedManager.load()
    }

    // Preload full-screen ads when the quiz opens.
    LaunchedEffect(lesson.id) {
        interstitialManager.load()
        rewardedManager.load()
    }

    // Show the interstitial once when the results screen appears.
    LaunchedEffect(showResults) {
        if (showResults && !interstitialShown) {
            interstitialShown = true
            activity?.let { interstitialManager.showIfReady(it) }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (showResults) "Sakamako" else "Tambaya ${currentIndex + 1} daga ${lesson.quiz.size}")
                },
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
        if (showResults) {
            val score = answers.count { it }
            LaunchedEffect(lesson.id, score) {
                progressStore.recordQuizResult(lesson.id, score)
            }
            QuizResults(
                score = score,
                total = lesson.quiz.size,
                onRetry = {
                    val shown = activity?.let {
                        rewardedManager.showIfReady(
                            activity = it,
                            onRewarded = { resetQuiz() }
                        )
                    } ?: false
                    // Fall back to a free retry when the ad isn't ready
                    // so learners are never blocked.
                    if (!shown) resetQuiz()
                },
                onFinish = onFinish,
                modifier = Modifier.padding(padding)
            )
            return@Scaffold
        }

        val question = lesson.quiz[currentIndex]
        val quizProgress = (currentIndex + 1).toFloat() / lesson.quiz.size

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentPadding = PaddingValues(HausaTechSpacing.Lg),
            verticalArrangement = Arrangement.spacedBy(HausaTechSpacing.Lg)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 640.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AnimatedProgressBar(
                            progress = quizProgress,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            "${currentIndex + 1}/${lesson.quiz.size}",
                            style = MaterialTheme.typography.labelLarge,
                            color = HausaTechColors.Accent,
                            modifier = Modifier.padding(start = HausaTechSpacing.Sm)
                        )
                    }
                }
            }

            item {
                Entrance {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            question.question,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(HausaTechSpacing.Xl)
                        )
                    }
                }
            }

            itemsIndexed(question.options) { index, option ->
                AnswerOption(
                    option = option,
                    state = when {
                        !answered -> AnswerState.Default
                        index == question.answerIndex -> AnswerState.Correct
                        index == selectedOption -> AnswerState.Wrong
                        else -> AnswerState.Dimmed
                    },
                    onClick = {
                        if (!answered) {
                            selectedOption = index
                            answered = true
                            answers.add(index == question.answerIndex)
                        }
                    }
                )
            }

            if (answered) {
                item {
                    Entrance {
                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                question.explanation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(HausaTechSpacing.Lg)
                            )
                        }
                    }
                }
                item {
                    PrimaryButton(
                        text = if (currentIndex < lesson.quiz.size - 1) "Na Gaba" else "Duba Sakamako",
                        icon = Icons.AutoMirrored.Filled.ArrowForward,
                        onClick = {
                            if (currentIndex < lesson.quiz.size - 1) {
                                currentIndex++
                                selectedOption = null
                                answered = false
                            } else {
                                showResults = true
                            }
                        }
                    )
                    // Breathing room so the button never sits against the ad banner.
                    Spacer(Modifier.height(HausaTechSpacing.Xl))
                }
            }
        }
    }
}

private enum class AnswerState { Default, Dimmed, Correct, Wrong }

/** Large rounded answer touch-target with clear visual states. */
@Composable
private fun AnswerOption(
    option: String,
    state: AnswerState,
    onClick: () -> Unit
) {
    val (container, border, content) = when (state) {
        AnswerState.Default -> Triple(
            MaterialTheme.colorScheme.surface,
            HausaTechColors.Outline,
            MaterialTheme.colorScheme.onSurface
        )
        AnswerState.Dimmed -> Triple(
            MaterialTheme.colorScheme.surface,
            HausaTechColors.Outline,
            MaterialTheme.colorScheme.onSurfaceVariant
        )
        AnswerState.Correct -> Triple(
            HausaTechColors.Success.copy(alpha = 0.14f),
            HausaTechColors.Success,
            MaterialTheme.colorScheme.onSurface
        )
        AnswerState.Wrong -> Triple(
            HausaTechColors.Error.copy(alpha = 0.14f),
            HausaTechColors.Error,
            MaterialTheme.colorScheme.onSurface
        )
    }
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = container),
        border = BorderStroke(1.5.dp, border),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(HausaTechSpacing.Lg),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (state == AnswerState.Correct) {
                Icon(
                    Icons.Filled.CheckCircle,
                    contentDescription = "Daidai",
                    tint = HausaTechColors.Success
                )
                Spacer(Modifier.width(HausaTechSpacing.Sm))
            }
            Text(
                option,
                style = MaterialTheme.typography.titleMedium,
                color = content,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/** Premium results hero: ring, score stats, encouragement, retry + return. */
@Composable
private fun QuizResults(
    score: Int,
    total: Int,
    onRetry: () -> Unit,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    val message = if (score >= 4) "Kyakkyawan aiki!"
    else if (score >= 2) "Ci gaba da koyo!"
    else "Ka sake duba darasin domin karin fahimta."

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(HausaTechSpacing.Lg),
        verticalArrangement = Arrangement.spacedBy(HausaTechSpacing.Lg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Entrance {
                OverallProgressCard(
                    completed = score,
                    total = total,
                    headline = "Maki: $score / $total",
                    subline = "Sakamakon jarabawa",
                    stats = {
                        StatRow(
                            stats = listOf(
                                "$score" to "Daidai",
                                "${total - score}" to "Kuskure",
                                "${(score.toFloat() / total * 100).toInt()}%" to "Kashi"
                            )
                        )
                    }
                )
            }
        }
        item {
            Text(
                message,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(HausaTechSpacing.Xs))
            Text(
                "Ka samu maki $score daga cikin $total",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 480.dp),
                verticalArrangement = Arrangement.spacedBy(HausaTechSpacing.Md)
            ) {
                PrimaryButton(
                    text = "Koma zuwa Darussa",
                    icon = Icons.Filled.PlayArrow,
                    onClick = onFinish
                )
                SecondaryButton(
                    text = "Sake Gwadawa (Kalli Talla)",
                    icon = Icons.Filled.Refresh,
                    onClick = onRetry
                )
                Spacer(Modifier.height(HausaTechSpacing.Xl))
            }
        }
    }
}
