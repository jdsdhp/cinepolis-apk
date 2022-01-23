package com.jdsdhp.cinepoliapp.di

import android.content.Context
import com.jdsdhp.cinepoliapp.data.database.AppDatabase
import com.jdsdhp.cinepoliapp.data.database.RoomAppDatabase
import com.jdsdhp.cinepoliapp.data.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    internal fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        RoomAppDatabase.getInstance(context)

    @Provides
    internal fun provideLocationDao(database: AppDatabase): LocationDao = database.locationDao()

    @Provides
    internal fun provideMovieDao(database: AppDatabase): MovieDao = database.movieDao()

    @Provides
    internal fun provideRouteDao(database: AppDatabase): RouteDao = database.routeDao()

}