package com.jdsdhp.cinepoliapp.data.database

import com.jdsdhp.cinepoliapp.data.database.dao.MovieDao

internal interface AppDatabase {
    fun movieDao(): MovieDao
}