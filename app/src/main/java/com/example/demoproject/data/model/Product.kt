package com.example.demoproject.data.model

import com.google.gson.annotations.SerializedName

data class Product(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("price")
    val price: Double,

    @field:SerializedName("images")
    val images: List<String>,

    var isSelected: Boolean = false,
)
