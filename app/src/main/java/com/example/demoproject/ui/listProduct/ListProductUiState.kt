package com.example.demoproject.ui.listProduct

import com.example.demoproject.model.Product

data class ListProductUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: List<Product>? = null,
)