package com.example.demoproject.data.repository


import android.util.Log
import com.example.demoproject.data.api.ProductsService
import com.example.demoproject.data.db.ProductDao
import com.example.demoproject.data.model.ProductEntity
import com.example.demoproject.data.mapper.toEntity
import com.example.demoproject.data.model.Product
import com.example.demoproject.util.LogMessages
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productsService: ProductsService,
    private val productDao: ProductDao,
) {

    companion object {
        private const val TAG = "ProductRepository"
    }

    suspend fun getProduct(): List<Product> {
        return try {
            productsService.getProduct().products
        } catch (e: Exception) {
            Log.e(TAG, LogMessages.ERROR_FETCH_PRODUCTS, e)
            emptyList()
        }
    }

    suspend fun saveProduct(userId: Long, product: Product) {
        try {
            productDao.insertProduct(product.toEntity(userId))
        } catch (e: Exception) {
            Log.e(TAG, LogMessages.ERROR_SAVE_PRODUCT, e)
        }
    }

    suspend fun getMyProduct(userId: Long): List<ProductEntity> {
        return try {
            productDao.getProductsByUserId(userId)
        } catch (e: Exception) {
            Log.e(TAG, LogMessages.ERROR_LOAD_USER_PRODUCTS, e)
            emptyList()
        }
    }

    suspend fun deleteProduct(productEntity: ProductEntity) {
        try {
            productDao.deleteProduct(productEntity)
        } catch (e: Exception) {
            Log.e(TAG, LogMessages.ERROR_DELETE_PRODUCT, e)
        }
    }
}
