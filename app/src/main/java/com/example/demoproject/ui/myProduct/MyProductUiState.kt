package com.example.demoproject.ui.myProduct

import com.example.demoproject.model.ProductEntity

data class MyProductUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: List<ProductEntity>? = null,
)