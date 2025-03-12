package com.example.demoproject.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.demoproject.data.model.ProductEntity

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM product_table WHERE user_id = :userId")
    suspend fun getProductsByUserId(userId: Long): List<ProductEntity>

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)
}
