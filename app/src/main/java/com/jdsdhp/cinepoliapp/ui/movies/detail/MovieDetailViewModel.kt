package com.jdsdhp.cinepoliapp.ui.movies.detail

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.jdsdhp.cinepoliapp.data.database.model.Movie
import com.jdsdhp.cinepoliapp.data.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val app: Application,
    private val movieRepo: MovieRepo,
) : ViewModel() {

    val movie: Movie = savedStateHandle.get(ARG_MOVIE_KEY)!!

    companion object {
        const val ARG_MOVIE_KEY = "movie"
    }

}