package com.jdsdhp.cinepoliapp.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.jdsdhp.cinepoliapp.data.database.model.Movie

@Dao
interface MovieDao : BasicDao<Movie> {

    @Query("SELECT * FROM movies")
    fun paginate(): PagingSource<Int, Movie>

}