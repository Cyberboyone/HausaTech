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
    val supplementaryDiagrams: List<SupplementaryDiagram>? = null,
    val quiz: List<QuizQuestion>
)

@Serializable
data class Diagram(
    val type: String,           // "image" | "interactive"
    val asset: String? = null,  // filename under assets/images/, when type == "image"
    val key: String? = null,    // maps to a composable in InteractiveDiagrams, when type == "interactive"
    val caption: String? = null // Hausa caption shown under the diagram
)

/** Lightweight diagrams rendered by Compose at runtime — no PNG assets needed. */
@Serializable
data class SupplementaryDiagram(
    val type: String,           // "flow" | "compare" | "stack" | "hub"
    val title: String,
    val items: List<String>? = null,       // for flow, stack
    val caption: String? = null,
    // compare-specific
    val leftTitle: String? = null,
    val leftItems: List<String>? = null,
    val rightTitle: String? = null,
    val rightItems: List<String>? = null,
    // hub-specific
    val center: String? = null,
    val satellites: List<String>? = null
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
