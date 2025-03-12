package com.example.demoproject.data.mapper

import com.example.demoproject.data.model.ProductEntity
import com.example.demoproject.data.model.Product

fun Product.toEntity(userId: Long): ProductEntity {
    return ProductEntity(
        title = title,
        price = price,
        image = images[0],
        userId = userId,
    )
}
