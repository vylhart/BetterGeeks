package com.example.bettergeeks.data.shared_preference

import android.content.Context
import android.content.SharedPreferences


object LocalCache {
    private const val PREF_NAME = "RequestPreference"
    private const val KEY_REQUEST_PREFIX = "request_"
    private const val KEY_TIMESTAMP_PREFIX = "timestamp_"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun isRequestProcessedWithinTimeWindow(context: Context, requestId: String): Boolean {
        val sharedPreferences = getSharedPreferences(context)

        val lastProcessedTimestamp = sharedPreferences.getLong(KEY_TIMESTAMP_PREFIX + requestId, 0)
        val currentTime = System.currentTimeMillis()

        // Check if the request was processed within the last 10 minutes
        return currentTime - lastProcessedTimestamp <= 10 * 60 * 1000
    }

    fun markRequestAsProcessed(context: Context, requestId: String) {
        val sharedPreferences = getSharedPreferences(context)

        // Save the current timestamp as the last processed timestamp for the request
        val currentTime = System.currentTimeMillis()
        sharedPreferences.edit {
            putLong(KEY_TIMESTAMP_PREFIX + requestId, currentTime)
        }
    }

    // Extension function to simplify SharedPreferences editing
    private inline fun SharedPreferences.edit(commit: Boolean = false, action: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        action(editor)
        if (commit) {
            editor.commit()
        } else {
            editor.apply()
        }
    }
}
