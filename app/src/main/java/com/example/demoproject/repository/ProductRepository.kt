package com.example.demoproject.repository


import com.example.demoproject.api.ProductsService
import com.example.demoproject.db.ProductDao
import com.example.demoproject.model.ProductEntity
import com.example.demoproject.mapper.toEntity
import com.example.demoproject.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productsService: ProductsService,
    private val productDao: ProductDao,
) {
    suspend fun getProduct(): List<Product> {
        val response = productsService.getProduct()
        return response.products
    }

    suspend fun saveProduct(userId: Long, product: Product) {
        productDao.insertProduct(
            product.toEntity(userId)
        )
    }

    suspend fun getMyProduct(userId: Long): List<ProductEntity> {
        val response = productDao.getProductsByUserId(userId)
        return response
    }

    suspend fun deleteProduct(productEntity: ProductEntity) {
        productDao.deleteProduct(productEntity)
    }
}