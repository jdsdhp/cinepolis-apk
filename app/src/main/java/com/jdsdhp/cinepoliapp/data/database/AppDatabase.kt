package com.jdsdhp.cinepoliapp.data.database

import com.jdsdhp.cinepoliapp.data.database.dao.*

internal interface AppDatabase {
    fun locationDao(): LocationDao
    fun mediaDao(): MediaDao
    fun movieDao(): MovieDao
    fun routeDao(): RouteDao
    fun settingsDao(): SettingsDao
    fun sizesDao(): SizesDao
}