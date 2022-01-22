package com.jdsdhp.cinepoliapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jdsdhp.cinepoliapp.data.database.RoomAppDatabase.Companion.DB_VERSION
import com.jdsdhp.cinepoliapp.data.store.model.*

@Database(
    entities = [Location::class, Media::class, Movie::class, Route::class, Settings::class, Sizes::class],
    version = DB_VERSION,
    exportSchema = false,
)
@TypeConverters(RoomConverters::class)
internal abstract class RoomAppDatabase : RoomDatabase(), AppDatabase {

    internal companion object {

        private const val DB_NAME = "data.db"
        internal const val DB_VERSION = 1

        // For Singleton instantiation
        @Volatile
        private var instance: RoomAppDatabase? = null

        internal fun getInstance(context: Context): RoomAppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context): RoomAppDatabase =
            Room.databaseBuilder(context, RoomAppDatabase::class.java, DB_NAME).build()

    }

}