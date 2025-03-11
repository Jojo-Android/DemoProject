package com.example.demoproject.repository

import com.example.demoproject.data.PreferencesHelper
import com.example.demoproject.db.UserDao
import com.example.demoproject.model.User
import com.example.demoproject.model.UserEntity
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val preferencesHelper: PreferencesHelper,
    private val userDao: UserDao,
) {
    suspend fun register(user: User) {
        userDao.insertUser(
            user = UserEntity(
                name = user.name,
                username = user.username,
                password = user.password,
            )
        )
    }

    suspend fun login(username: String, password: String): UserEntity? {
        val user = userDao.getUserByUsername(username)
        if (user != null) {
            preferencesHelper.saveUserId(user.id)
            return user
        }
        return null
    }

    suspend fun isExistingUser(username: String): Boolean {
        val existingUser = userDao.getUserByUsername(username)
        return existingUser != null
    }


    fun logout() {
        preferencesHelper.clearUserId()
    }
}