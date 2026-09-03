package com.nakudin.techhausa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nakudin.techhausa.ui.components.BottomNavBar
import com.nakudin.techhausa.ui.screens.*
import com.nakudin.techhausa.ui.theme.TechHausaTheme

// Routes that show the bottom navigation bar. Everything else (course browsing,
// lesson reading, quizzes) is pushed full-screen on top without it, so the
// bottom bar only ever appears at the two top-level destinations.
private val TOP_LEVEL_ROUTES = setOf("home", "progress")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechHausaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val nav = rememberNavController()
                    val backStackEntry by nav.currentBackStackEntryAsState()
                    val currentRoute = backStackEntry?.destination?.route

                    Scaffold(
                        bottomBar = {
                            if (currentRoute in TOP_LEVEL_ROUTES) {
                                BottomNavBar(currentRoute = currentRoute) { route ->
                                    if (route != currentRoute) {
                                        nav.navigate(route) {
                                            popUpTo("home") { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                }
                            }
                        }
                    ) { scaffoldPadding ->
                        NavHost(
                            navController = nav,
                            startDestination = "home",
                            modifier = Modifier.padding(scaffoldPadding)
                        ) {

                            composable("home") {
                                HomeScreen(
                                    onOpenCourse = { courseId -> nav.navigate("course/$courseId") },
                                    onOpenLesson = { courseId, lessonId ->
                                        nav.navigate("course/$courseId/lesson/$lessonId")
                                    }
                                )
                            }

                            composable("progress") {
                                ProgressScreen(
                                    onOpenCourse = { courseId -> nav.navigate("course/$courseId") }
                                )
                            }

                            composable(
                                "course/{courseId}",
                                arguments = listOf(navArgument("courseId") { type = NavType.StringType })
                            ) { entry ->
                                val courseId = entry.arguments?.getString("courseId") ?: return@composable
                                LevelListScreen(
                                    courseId = courseId,
                                    onBack = { nav.popBackStack() },
                                    onOpenLevel = { level -> nav.navigate("course/$courseId/level/$level") }
                                )
                            }

                            composable(
                                "course/{courseId}/level/{level}",
                                arguments = listOf(
                                    navArgument("courseId") { type = NavType.StringType },
                                    navArgument("level") { type = NavType.StringType }
                                )
                            ) { entry ->
                                val courseId = entry.arguments?.getString("courseId") ?: return@composable
                                val level = entry.arguments?.getString("level") ?: return@composable
                                LessonListScreen(
                                    courseId = courseId,
                                    level = level,
                                    onBack = { nav.popBackStack() },
                                    onOpenLesson = { lessonId -> nav.navigate("course/$courseId/lesson/$lessonId") }
                                )
                            }

                            composable(
                                "course/{courseId}/lesson/{lessonId}",
                                arguments = listOf(
                                    navArgument("courseId") { type = NavType.StringType },
                                    navArgument("lessonId") { type = NavType.StringType }
                                )
                            ) { entry ->
                                val courseId = entry.arguments?.getString("courseId") ?: return@composable
                                val lessonId = entry.arguments?.getString("lessonId") ?: return@composable
                                LessonScreen(
                                    courseId = courseId,
                                    lessonId = lessonId,
                                    onBack = { nav.popBackStack() },
                                    onStartQuiz = { id -> nav.navigate("course/$courseId/quiz/$id") }
                                )
                            }

                            composable(
                                "course/{courseId}/quiz/{lessonId}",
                                arguments = listOf(
                                    navArgument("courseId") { type = NavType.StringType },
                                    navArgument("lessonId") { type = NavType.StringType }
                                )
                            ) { entry ->
                                val courseId = entry.arguments?.getString("courseId") ?: return@composable
                                val lessonId = entry.arguments?.getString("lessonId") ?: return@composable
                                QuizScreen(
                                    courseId = courseId,
                                    lessonId = lessonId,
                                    onBack = { nav.popBackStack() },
                                    onFinish = {
                                        // Pop Quiz then Lesson screens to land on this level's
                                        // Lesson List, where the just-completed lesson's progress
                                        // is visible and the next lesson is one tap away.
                                        nav.popBackStack()
                                        nav.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
