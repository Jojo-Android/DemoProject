package com.example.demoproject.ui.register

import com.example.demoproject.ui.BaseUiState

data class RegisterUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val isSuccess: Boolean = false
) : BaseUiState<Unit>(isLoading, error, null)
