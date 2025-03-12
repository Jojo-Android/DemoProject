package com.example.demoproject.ui

abstract class BaseUiState<T>(
    open val isLoading: Boolean = false,
    open val error: String? = null,
    open val data: T? = null
)