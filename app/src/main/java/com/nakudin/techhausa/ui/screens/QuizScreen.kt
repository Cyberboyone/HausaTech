package com.nakudin.techhausa.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nakudin.techhausa.ads.InterstitialManager
import com.nakudin.techhausa.ads.RewardedManager
import com.nakudin.techhausa.data.CourseRepository
import com.nakudin.techhausa.data.ProgressStore
import com.nakudin.techhausa.ui.components.AdBanner

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
        Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text("Ba a sami jarabawa ba")
        }
        return
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var answered by remember { mutableStateOf(false) }
    val answers = remember { mutableStateListOf<Boolean>() }
    var showResults by remember { mutableStateOf(false) }

    val activity = context as? Activity
    val interstitialManager = remember { InterstitialManager(context) }
    val rewardedManager = remember { RewardedManager(context) }

    var interstitialShown by remember { mutableStateOf(false) }

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
                title = { Text(if (showResults) "Sakamako" else "Tambaya ${currentIndex + 1}/${lesson.quiz.size}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Koma baya")
                    }
                }
            )
        },
        bottomBar = { AdBanner() }
    ) { padding ->
        if (showResults) {
            val score = answers.count { it }
            LaunchedEffect(lesson.id, score) {
                progressStore.recordQuizResult(lesson.id, score)
            }
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Ka samu maki $score daga cikin ${lesson.quiz.size}", style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(8.dp))
                Text(
                    if (score >= 4) "Kyakkyawan aiki!" else if (score >= 2) "Ci gaba da koyo!" else "Ka sake duba darasin domin karin fahimta.",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(24.dp))
                Button(onClick = onFinish) {
                    Text("Koma zuwa Darussa")
                }
                Spacer(Modifier.height(12.dp))
                OutlinedButton(onClick = {
                    val shown = activity?.let {
                        rewardedManager.showIfReady(
                            activity = it,
                            onRewarded = { resetQuiz() }
                        )
                    } ?: false
                    // Fall back to a free retry when the ad isn't ready
                    // so learners are never blocked.
                    if (!shown) resetQuiz()
                }) {
                    Text("Sake Gwadawa (Kalli Talla)")
                }
            }
            return@Scaffold
        }

        val question = lesson.quiz[currentIndex]

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Text(question.question, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            question.options.forEachIndexed { index, option ->
                val isCorrect = index == question.answerIndex
                val cardColor = when {
                    !answered -> MaterialTheme.colorScheme.surfaceVariant
                    isCorrect -> MaterialTheme.colorScheme.primaryContainer
                    index == selectedOption -> MaterialTheme.colorScheme.errorContainer
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .selectable(
                            selected = selectedOption == index,
                            enabled = !answered,
                            onClick = {
                                selectedOption = index
                                answered = true
                                answers.add(isCorrect)
                            }
                        ),
                    colors = CardDefaults.cardColors(containerColor = cardColor)
                ) {
                    Text(option, modifier = Modifier.padding(16.dp))
                }
            }

            if (answered) {
                Spacer(Modifier.height(12.dp))
                Text(
                    question.explanation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(16.dp))
                Button(onClick = {
                    if (currentIndex < lesson.quiz.size - 1) {
                        currentIndex++
                        selectedOption = null
                        answered = false
                    } else {
                        showResults = true
                    }
                }) {
                    Text(if (currentIndex < lesson.quiz.size - 1) "Na Gaba" else "Duba Sakamako")
                }
            }
        }
    }
}
