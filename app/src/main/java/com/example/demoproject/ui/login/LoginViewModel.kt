package com.example.demoproject.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoproject.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _loginUiStateLiveData = MutableLiveData<LoginUiState>()
    val loginUiStateLiveData: LiveData<LoginUiState>
        get() = _loginUiStateLiveData


    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password


    fun setUsername(newUsername: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _username.postValue(newUsername)
        }

    }


    fun setPassword(newPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _password.postValue(newPassword)
        }
    }


    fun login(username: String, password: String) {
        viewModelScope.launch {
            if (username.isBlank() || password.isBlank()) {
                _loginUiStateLiveData.value = LoginUiState(
                    isLoading = false,
                    isSuccess = false,
                    error = "Username and password cannot be empty"
                )
                return@launch
            }

            _loginUiStateLiveData.value = LoginUiState(
                isLoading = true,
                isSuccess = false,
                error = null
            )

            delay(1500L)

            try {
                val user = authRepository.login(username, password)
                _loginUiStateLiveData.value = if (user != null) {
                    LoginUiState(isLoading = false, isSuccess = true, error = null)
                } else {
                    LoginUiState(
                        isLoading = false,
                        isSuccess = false,
                        error = "Incorrect username or password"
                    )
                }

            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error logging in", e)
                _loginUiStateLiveData.value = LoginUiState(
                    isLoading = false,
                    isSuccess = false,
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }


}