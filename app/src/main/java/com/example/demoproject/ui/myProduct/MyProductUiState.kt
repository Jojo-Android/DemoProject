package com.example.demoproject.ui.myProduct

import com.example.demoproject.ui.BaseUiState
import com.example.demoproject.data.model.ProductEntity

data class MyProductUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    override val data: List<ProductEntity>? = null
) : BaseUiState<List<ProductEntity>>(isLoading, error, data)
