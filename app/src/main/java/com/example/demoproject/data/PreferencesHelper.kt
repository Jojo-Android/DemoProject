package com.example.demoproject.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.demoproject.util.LogMessages
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val PREF_NAME = "user_prefs"
    private val KEY_USER_ID = "user_id"

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val TAG = "PreferencesHelper"
    }

    fun saveUserId(userId: Long) {
        try {
            sharedPreferences.edit {
                putLong(KEY_USER_ID, userId)
            }
        } catch (e: Exception) {
            Log.e(TAG, LogMessages.ERROR_SAVE_USER, e)
        }

    }

    fun getUserId(): Long {
        return try {
            sharedPreferences.getLong(KEY_USER_ID, -1)
        } catch (e: Exception) {
            Log.e(TAG, LogMessages.ERROR_FETCH_USER, e)
            -1
        }
    }

    fun clearUserId() {
        try {
            sharedPreferences.edit {
                remove(KEY_USER_ID)
            }
        } catch (e: Exception) {
            Log.e(TAG, LogMessages.ERROR_CLEAR_USER, e)
        }
    }
}
