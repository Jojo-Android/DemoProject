package com.example.demoproject.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoproject.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _loginUiStateLiveData = MutableLiveData<LoginUiState>()
    val loginUiStateLiveData: LiveData<LoginUiState>
        get() = _loginUiStateLiveData


    fun login(username: String, password: String) {
        viewModelScope.launch() {
            _loginUiStateLiveData.postValue(
                LoginUiState(
                    isLoading = true,
                    isSuccess = false,
                    error = null
                )
            )
            try {
                val user = authRepository.login(username, password)
                if (user != null) {
                    _loginUiStateLiveData.value = LoginUiState(
                        isLoading = false,
                        isSuccess = true,
                        error = null,
                    )
                } else {
                    _loginUiStateLiveData.value = LoginUiState(
                        isLoading = false,
                        isSuccess = false,
                        error = "Incorrect username or password",
                    )
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error to login", e)
                _loginUiStateLiveData.postValue(
                    LoginUiState(
                        isLoading = false,
                        isSuccess = false,
                        error = e.message,
                    )
                )
            }
        }
    }
}