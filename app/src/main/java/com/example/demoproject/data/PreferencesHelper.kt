package com.example.demoproject.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PreferencesHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val PREF_NAME = "user_prefs"
    private val KEY_USER_ID = "user_id"

    private val sharedPreferences: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveUserId(userId: Long) {
        sharedPreferences.edit {
            putLong(KEY_USER_ID, userId)
        }
    }

    fun getUserId(): Long {
        return sharedPreferences.getLong(KEY_USER_ID, -1)
    }

    fun clearUserId() {
        sharedPreferences.edit {
            remove(KEY_USER_ID)
        }
    }
}
