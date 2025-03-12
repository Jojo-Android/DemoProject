package com.example.demoproject.data.model

import com.google.gson.annotations.SerializedName

data class ResponseProducts(
    @field:SerializedName("products")
    val products: List<Product> ,
)
