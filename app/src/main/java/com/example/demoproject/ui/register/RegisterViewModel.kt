package com.example.demoproject.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoproject.data.model.User
import com.example.demoproject.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _registerUiState = MutableLiveData<RegisterUiState>()
    val registerUiState: LiveData<RegisterUiState>
        get() = _registerUiState

    fun register(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            _registerUiState.postValue(
                RegisterUiState(
                    isLoading = true,
                    isSuccess = false,
                    error = null
                )
            )

            try {
                if (user.name.isBlank() || user.username.isBlank() || user.password.isBlank()) {
                    _registerUiState.postValue(
                        RegisterUiState(
                            isLoading = false,
                            isSuccess = false,
                            error = "All fields are required."
                        )
                    )
                    return@launch
                }

                if (authRepository.isExistingUser(username = user.username)) {
                    _registerUiState.postValue(
                        RegisterUiState(
                            isLoading = false,
                            isSuccess = false,
                            error = "User already exists. Please choose a different username."
                        )
                    )
                    return@launch
                }else{
                    authRepository.register(user)
                    _registerUiState.postValue(
                        RegisterUiState(
                            isLoading = false,
                            isSuccess = true,
                            error = null
                        )
                    )
                }


            } catch (e: Exception) {
                _registerUiState.postValue(
                    RegisterUiState(
                        isLoading = false,
                        isSuccess = false,
                        error = "Registration failed: ${e.message}"
                    )
                )
            }
        }
    }

}