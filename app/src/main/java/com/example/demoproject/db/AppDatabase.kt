package com.example.demoproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demoproject.model.ProductEntity
import com.example.demoproject.model.UserEntity

@Database(entities = [UserEntity::class, ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
}
