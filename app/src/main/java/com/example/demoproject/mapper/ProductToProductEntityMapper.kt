package com.example.demoproject.mapper

import com.example.demoproject.model.ProductEntity
import com.example.demoproject.model.Product

fun Product.toEntity(userId: Long): ProductEntity {
    return ProductEntity(
        title = title,
        price = price,
        image = images[0],
        userId = userId,
    )
}
