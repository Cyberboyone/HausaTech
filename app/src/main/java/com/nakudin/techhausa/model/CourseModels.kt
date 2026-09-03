package com.nakudin.techhausa.model

import kotlinx.serialization.Serializable

@Serializable
data class CourseFile(
    val id: String,
    val title: String,          // English, e.g. "Cybersecurity"
    val icon: String,           // maps to a Material icon name in CourseIcons
    val levels: List<CourseLevel>
)

@Serializable
data class CourseLevel(
    val level: String,          // "Beginner" | "Intermediate" | "Advanced" (English)
    val lessons: List<Lesson>
)

@Serializable
data class Lesson(
    val id: String,
    val order: Int,
    val title: String,          // Hausa
    val summary: String,        // Hausa
    val content: String,        // Hausa
    val diagram: Diagram? = null,
    val quiz: List<QuizQuestion>
)

@Serializable
data class Diagram(
    val type: String,           // "image" | "interactive"
    val asset: String? = null,  // filename under assets/images/, when type == "image"
    val key: String? = null,    // maps to a composable in InteractiveDiagrams, when type == "interactive"
    val caption: String? = null // Hausa caption shown under the diagram
)

@Serializable
data class QuizQuestion(
    val question: String,       // Hausa
    val options: List<String>,  // Hausa
    val answerIndex: Int,
    val explanation: String     // Hausa
)

/** Flat, in-memory representation used once a course file is loaded and levels/lessons are indexed. */
data class CourseSummary(
    val id: String,
    val title: String,
    val icon: String,
    val totalLessons: Int
)
