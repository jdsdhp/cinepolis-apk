package com.jdsdhp.cinepoliapp.ui.profile

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.data.api.ResponseResult
import com.jdsdhp.cinepoliapp.data.repository.ProfileRepo
import com.jdsdhp.cinepoliapp.data.store.model.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal data class ProfileUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoadSuccess: Boolean = false
)

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val app: Application,
    private val profileRepo: ProfileRepo,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        refreshData()
    }

    val profile: LiveData<Profile?> = profileRepo.profileFlow.asLiveData()

    @Suppress("DeferredResultUnused")
    fun refreshData() = viewModelScope.launch {
        async { fetchProfile() }
    }

    private fun fetchProfile() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null, isLoadSuccess = false) }
        when (val response = profileRepo.fetchProfile()) {
            is ResponseResult.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        isLoadSuccess = true,
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
                        isLoadSuccess = false,
                    )
                }
            }
        }
    }

}