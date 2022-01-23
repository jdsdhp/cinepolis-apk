package com.jdsdhp.cinepoliapp.data.database

import com.jdsdhp.cinepoliapp.data.database.dao.LocationDao
import com.jdsdhp.cinepoliapp.data.database.dao.MovieDao
import com.jdsdhp.cinepoliapp.data.database.dao.RouteDao

internal interface AppDatabase {
    fun locationDao(): LocationDao
    fun movieDao(): MovieDao
    fun routeDao(): RouteDao
}