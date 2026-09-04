package com.nakudin.techhausa.data

import android.content.Context
import com.nakudin.techhausa.model.CourseFile
import com.nakudin.techhausa.model.CourseSummary
import com.nakudin.techhausa.model.Lesson
import kotlinx.serialization.json.Json

/**
 * Loads course JSON files from the bundled assets/courses folder. Each file
 * is one course (English title) with 3 levels (English) and Hausa lesson
 * content.
 *
 * Adding a new course later = drop another JSON file with this shape into
 * assets/courses and add its filename to COURSE_FILES below — no other
 * code changes needed.
 */
object CourseRepository {

    // Bundled course files. Add new filenames here as more courses are authored.
    private val COURSE_FILES = listOf(
        "cybersecurity.json",
        "ai.json",
        "cloud_computing.json",
        "networking.json",
        "programming.json",
        "web_development.json",
        "data_science.json",
        "mobile_development.json"
    )

    private var cachedCourses: List<CourseFile>? = null
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    fun getCourses(context: Context): List<CourseFile> {
        cachedCourses?.let { return it }
        val loaded = COURSE_FILES.mapNotNull { filename ->
            runCatching {
                val text = context.assets.open("courses/$filename").bufferedReader(Charsets.UTF_8).use { it.readText() }
                json.decodeFromString(CourseFile.serializer(), text)
            }.getOrElse { e1 ->
                android.util.Log.e("CourseRepository", "Full parse failed for $filename, retrying without supplementaryDiagrams", e1)
                runCatching {
                    val text = context.assets.open("courses/$filename").bufferedReader(Charsets.UTF_8).use { it.readText() }
                    val stripped = text.replace(Regex("\"supplementaryDiagrams\"\\s*:\\s*\\[.*?\\],\\s*\n"), "")
                    json.decodeFromString(CourseFile.serializer(), stripped)
                }.onFailure { e2 ->
                    android.util.Log.e("CourseRepository", "Even fallback parse failed for $filename", e2)
                }.getOrNull()
            }
        }
        android.util.Log.d("CourseRepository", "Loaded ${loaded.size}/${COURSE_FILES.size} courses")
        cachedCourses = loaded
        return loaded
    }

    fun getCourseSummaries(context: Context): List<CourseSummary> =
        getCourses(context).map { course ->
            CourseSummary(
                id = course.id,
                title = course.title,
                icon = course.icon,
                totalLessons = course.levels.sumOf { it.lessons.size }
            )
        }

    fun getCourse(context: Context, courseId: String): CourseFile? =
        getCourses(context).firstOrNull { it.id == courseId }

    /** Flattened list of every lesson in a course, in level-then-order sequence, tagged with its level name. */
    fun getLessonsWithLevel(context: Context, courseId: String): List<Pair<String, Lesson>> {
        val course = getCourse(context, courseId) ?: return emptyList()
        return course.levels.flatMap { level -> level.lessons.map { level.level to it } }
    }

    fun getLesson(context: Context, courseId: String, lessonId: String): Lesson? =
        getLessonsWithLevel(context, courseId).map { it.second }.firstOrNull { it.id == lessonId }

    fun getLevelForLesson(context: Context, courseId: String, lessonId: String): String? =
        getLessonsWithLevel(context, courseId).firstOrNull { it.second.id == lessonId }?.first

    /** All lesson IDs in one course — used for that course's progress bar. */
    fun getLessonIdsForCourse(context: Context, courseId: String): List<String> =
        getLessonsWithLevel(context, courseId).map { it.second.id }

    /** Every lesson ID across every course — used for the app-wide progress total. */
    fun getAllLessonIds(context: Context): List<String> =
        getCourses(context).flatMap { c -> c.levels.flatMap { it.lessons.map { l -> l.id } } }

    /** Simple case-insensitive title/summary search across every lesson, for the Home screen search bar. */
    data class SearchResult(val courseId: String, val courseTitle: String, val lesson: Lesson)

    fun searchLessons(context: Context, query: String): List<SearchResult> {
        if (query.isBlank()) return emptyList()
        val q = query.trim()
        return getCourses(context).flatMap { course ->
            course.levels.flatMap { level ->
                level.lessons
                    .filter { it.title.contains(q, ignoreCase = true) || it.summary.contains(q, ignoreCase = true) }
                    .map { SearchResult(course.id, course.title, it) }
            }
        }
    }
}
