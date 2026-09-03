package com.nakudin.techhausa.ads

import android.content.Context

/**
 * Keeps full-screen ads occasional so learning is never interrupted too often.
 * The count survives activity recreation and app restarts.
 */
class AdFrequencyController(context: Context) {
    private val prefs = context.getSharedPreferences("ad_frequency", Context.MODE_PRIVATE)

    companion object {
        private const val QUIZ_COMPLETIONS = "quiz_completions"
        private const val LAST_INTERSTITIAL_MS = "last_interstitial_ms"
        private const val QUIZZES_BETWEEN_ADS = 4
        private const val MIN_INTERVAL_MS = 10 * 60 * 1000L // 10 minutes
    }

    fun recordQuizCompletion() {
        val count = prefs.getInt(QUIZ_COMPLETIONS, 0) + 1
        prefs.edit().putInt(QUIZ_COMPLETIONS, count).apply()
    }

    fun shouldShowInterstitial(now: Long = System.currentTimeMillis()): Boolean {
        val completions = prefs.getInt(QUIZ_COMPLETIONS, 0)
        val lastShown = prefs.getLong(LAST_INTERSTITIAL_MS, 0L)
        return completions >= QUIZZES_BETWEEN_ADS && now - lastShown >= MIN_INTERVAL_MS
    }

    fun markInterstitialShown() {
        prefs.edit()
            .putInt(QUIZ_COMPLETIONS, 0)
            .putLong(LAST_INTERSTITIAL_MS, System.currentTimeMillis())
            .apply()
    }
}
