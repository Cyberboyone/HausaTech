package com.nakudin.techhausa.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "tech_hausa_progress")

private val LAST_COURSE_KEY = stringPreferencesKey("last_course_id")
private val LAST_LESSON_KEY = stringPreferencesKey("last_lesson_id")

/** Where the user should land if they tap "Continue" from the Home screen. */
data class LastAccessed(val courseId: String, val lessonId: String)

/**
 * Tracks completed lessons, best quiz scores, and the most recently opened
 * lesson, keyed by lessonId (unique across courses since each lesson id is
 * prefixed per-course, e.g. "cyber-b1", "ai-i3"). Entirely on-device, no
 * account needed.
 */
class ProgressStore(private val context: Context) {

    private fun completedKey(lessonId: String) = booleanPreferencesKey("completed_$lessonId")
    private fun scoreKey(lessonId: String) = intPreferencesKey("score_$lessonId")

    fun isCompleted(lessonId: String): Flow<Boolean> =
        context.dataStore.data.map { it[completedKey(lessonId)] ?: false }

    fun bestScore(lessonId: String): Flow<Int> =
        context.dataStore.data.map { it[scoreKey(lessonId)] ?: -1 }

    /**
     * Counts how many of [lessonIds] are marked completed, in a single pass
     * over the preferences map — used for course/level progress bars and the
     * app-wide completion count, so it stays cheap even summed across all
     * 144 lessons on the Progress screen.
     */
    fun completedCountFlow(lessonIds: List<String>): Flow<Int> =
        context.dataStore.data.map { prefs ->
            lessonIds.count { prefs[completedKey(it)] == true }
        }

    /** Where the user last opened a lesson, for the Home screen's "Continue" card. */
    fun lastAccessedFlow(): Flow<LastAccessed?> =
        context.dataStore.data.map { prefs ->
            val courseId = prefs[LAST_COURSE_KEY]
            val lessonId = prefs[LAST_LESSON_KEY]
            if (courseId != null && lessonId != null) LastAccessed(courseId, lessonId) else null
        }

    suspend fun recordLastAccessed(courseId: String, lessonId: String) {
        context.dataStore.edit { prefs ->
            prefs[LAST_COURSE_KEY] = courseId
            prefs[LAST_LESSON_KEY] = lessonId
        }
    }

    suspend fun recordQuizResult(lessonId: String, scoreOutOfFive: Int) {
        context.dataStore.edit { prefs ->
            val previousBest = prefs[scoreKey(lessonId)] ?: -1
            if (scoreOutOfFive > previousBest) {
                prefs[scoreKey(lessonId)] = scoreOutOfFive
            }
            prefs[completedKey(lessonId)] = true
        }
    }
}
