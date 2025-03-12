package com.example.demoproject.ui.login

import com.example.demoproject.ui.BaseUiState

data class LoginUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val isSuccess: Boolean = false
) : BaseUiState<Unit>(isLoading, error, null)