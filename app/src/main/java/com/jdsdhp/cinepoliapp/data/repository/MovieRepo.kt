package com.jdsdhp.cinepoliapp.data.repository

import androidx.paging.PagingSource
import com.jdsdhp.cinepoliapp.data.api.ResponseResult
import com.jdsdhp.cinepoliapp.data.database.model.Movie

internal interface MovieRepo {

    fun paginateMovies(): PagingSource<Int, Movie>

    suspend fun fetchMovies(cinema: Int): ResponseResult<Nothing>

}