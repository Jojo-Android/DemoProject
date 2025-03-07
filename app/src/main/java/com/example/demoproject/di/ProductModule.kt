package com.example.demoproject.di


import android.app.Application
import androidx.room.Room
import com.example.demoproject.api.ProductsService
import com.example.demoproject.db.ProductDao
import com.example.demoproject.db.AppDatabase
import com.example.demoproject.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ProductModule {

    @Provides
    @Singleton
    fun provideProductDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "product_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Singleton
    @Provides
    fun provideProductsService(): ProductsService {
        return ProductsService.create()
    }

}