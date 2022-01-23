package com.jdsdhp.cinepoliapp.ui.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.data.api.ResponseResult
import com.jdsdhp.cinepoliapp.data.repository.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isUserLoggedIn: Boolean = false
)

@HiltViewModel
internal class LoginViewModel @Inject constructor(
    private val app: Application,
    private val authRepo: AuthRepo,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun sendLogin(email: String, password: String) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null, isUserLoggedIn = false) }
        when (val response = authRepo.sendLogin(email = email, password = password)) {
            is ResponseResult.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        isUserLoggedIn = true
                    )
                }
            }
            is ResponseResult.Error -> {
                val errorMessage =
                    if (response.exception.second.isBlank()) app.getString(R.string.http_error_default)
                    else response.exception.second

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = errorMessage,
                        isUserLoggedIn = false,
                    )
                }
            }
        }

    }

}