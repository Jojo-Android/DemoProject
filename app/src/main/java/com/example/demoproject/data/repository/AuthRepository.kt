package com.example.demoproject.data.repository

import com.example.demoproject.data.PreferencesHelper
import com.example.demoproject.data.db.UserDao
import com.example.demoproject.data.model.User
import com.example.demoproject.data.model.UserEntity
import javax.inject.Inject
import android.util.Log
import com.example.demoproject.util.LogMessages

class AuthRepository @Inject constructor(
    private val preferencesHelper: PreferencesHelper,
    private val userDao: UserDao,
) {
    companion object {
        private const val TAG = "AuthRepository"
    }

    suspend fun register(user: User) {
        try {
            userDao.insertUser(
                user = UserEntity(
                    name = user.name,
                    username = user.username,
                    password = user.password,
                )
            )
        } catch (e: Exception) {
            Log.e(TAG, LogMessages.ERROR_REGISTER, e)
        }
    }

    suspend fun login(username: String, password: String): UserEntity? {
        return try {
            val user = userDao.getUserByUsernameAndPassword(username, password)
            user?.let { preferencesHelper.saveUserId(it.id) }
            user
        } catch (e: Exception) {
            Log.e(TAG, LogMessages.ERROR_LOGIN, e)
            null
        }
    }

    suspend fun isExistingUser(username: String): Boolean {
        return try {
            val existingUser = userDao.getUserByUsername(username)
            existingUser != null
        } catch (e: Exception) {
            Log.e(TAG, LogMessages.ERROR_CHECK_USER, e)
            false
        }
    }

    fun logout() {
        try {
            preferencesHelper.clearUserId()
        } catch (e: Exception) {
            Log.e(TAG, LogMessages.ERROR_LOGOUT, e)
        }
    }
}
