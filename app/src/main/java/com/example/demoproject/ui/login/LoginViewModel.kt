package com.example.demoproject.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoproject.R
import com.example.demoproject.data.repository.AuthRepository
import com.example.demoproject.util.LogMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val _loginUiStateLiveData = MutableLiveData<LoginUiState>()
    val loginUiStateLiveData: LiveData<LoginUiState>
        get() = _loginUiStateLiveData


    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> get() = _password

    companion object {
        private const val TAG = "LoginViewModel"
    }

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
                    error = context.getString(R.string.error_empty_credentials)
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
                        error = context.getString(R.string.error_incorrect_credentials)
                    )
                }

            } catch (e: Exception) {
                Log.e(TAG, LogMessages.ERROR_LOGIN, e)
                _loginUiStateLiveData.value = LoginUiState(
                    isLoading = false,
                    isSuccess = false,
                    error = context.getString(R.string.error_unexpected, e.message)
                )
            }
        }
    }


}