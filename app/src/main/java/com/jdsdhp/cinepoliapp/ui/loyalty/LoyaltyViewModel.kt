package com.jdsdhp.cinepoliapp.ui.loyalty

import android.app.Application
import androidx.lifecycle.*
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.data.api.ResponseResult
import com.jdsdhp.cinepoliapp.data.api.mappers.LoyaltyResponse
import com.jdsdhp.cinepoliapp.data.repository.LoyaltyRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal data class LoyaltyUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoadSuccess: Boolean = false,
)

@HiltViewModel
internal class LoyaltyViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val app: Application,
    private val loyaltyRepo: LoyaltyRepo,
) : ViewModel() {

    private val cardNumber: String = savedStateHandle.get(ARG_CARD_KEY)!!
    private val countryCode: String = savedStateHandle.get(ARG_COUNTRY_CODE_KEY)!!
    private val pin: String = savedStateHandle.get(ARG_PIN_KEY)!!

    private val _uiState = MutableStateFlow(LoyaltyUiState())
    val uiState: StateFlow<LoyaltyUiState> = _uiState.asStateFlow()

    private val _loyalty = MutableLiveData<LoyaltyResponse>()
    val loyalty: LiveData<LoyaltyResponse> = _loyalty

    init {
        fetchLoyalty()
    }

    fun fetchLoyalty() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null, isLoadSuccess = false) }
        when (val response = loyaltyRepo.fetchLoyalty(
            cardNumber = cardNumber,
            pin = pin,
            countryCode = countryCode,
        )) {
            is ResponseResult.Success -> {
                if (response.data != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            isLoadSuccess = true,
                        )
                    }
                    _loyalty.value = response.data!!
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = app.getString(R.string.no_loyalty_data_found),
                            isLoadSuccess = false,
                        )
                    }
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

    companion object {
        const val ARG_CARD_KEY = "card_number"
        const val ARG_COUNTRY_CODE_KEY = "country_code"
        const val ARG_PIN_KEY = "pin"
        const val ARG_TRANSACTION_INCLUDE_KEY = "transaction_include"
    }

}