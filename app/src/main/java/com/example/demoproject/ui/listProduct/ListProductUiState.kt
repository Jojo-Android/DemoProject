package com.example.demoproject.ui.listProduct

import com.example.demoproject.data.model.Product
import com.example.demoproject.ui.BaseUiState

data class ListProductUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    override val data: List<Product>? = null
) : BaseUiState<List<Product>>(isLoading, error, data)
