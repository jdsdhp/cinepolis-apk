package com.jdsdhp.cinepoliapp.ui.movies

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jdsdhp.cinepoliapp.R
import com.jdsdhp.cinepoliapp.data.api.ResponseResult
import com.jdsdhp.cinepoliapp.data.database.model.Movie
import com.jdsdhp.cinepoliapp.data.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

internal data class MoviesUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoadSuccess: Boolean = false
)

@HiltViewModel
internal class MoviesViewModel @Inject constructor(
    private val app: Application,
    private val movieRepo: MovieRepo,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesUiState())
    val uiState: StateFlow<MoviesUiState> = _uiState.asStateFlow()

    init {
        fetchMovies()
    }

    fun fetchMovies() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, errorMessage = null, isLoadSuccess = false) }
        when (val response = movieRepo.fetchMovies(cinema = 61)) {
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

    fun paginateMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false,
            maxSize = 90,
        )
    ) { movieRepo.paginateMovies() }.flow.cachedIn(viewModelScope)

}