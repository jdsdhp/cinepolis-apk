package com.jdsdhp.cinepoliapp.data.repository

import androidx.paging.PagingSource
import com.jdsdhp.cinepoliapp.data.api.ApiService
import com.jdsdhp.cinepoliapp.data.api.RequestHandler
import com.jdsdhp.cinepoliapp.data.api.ResponseResult
import com.jdsdhp.cinepoliapp.data.api.mappers.toMovie
import com.jdsdhp.cinepoliapp.data.database.dao.MovieDao
import com.jdsdhp.cinepoliapp.data.database.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class MovieRepoImpl internal constructor(
    private val service: ApiService,
    private val requestHandler: RequestHandler,
    private val movieDao: MovieDao,
) : MovieRepo {

    override fun paginateMovies(): PagingSource<Int, Movie> = movieDao.paginate()

    override suspend fun fetchMovies(cinema: Int): ResponseResult<Nothing> =
        withContext(Dispatchers.IO) {
            val res = requestHandler.safeApiCall { service.fetchMovies() }

            if (res is ResponseResult.Success && res.data != null) {
                movieDao.insert(res.data.movieRes.map { it.toMovie(res.data.routeRes) })
                return@withContext ResponseResult.Success()
            } else {
                return@withContext if (res is ResponseResult.Error) res
                else ResponseResult.Error.DEFAULT
            }
        }

}