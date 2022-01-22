package com.jdsdhp.cinepoliapp.di

import android.content.Context
import com.jdsdhp.cinepoliapp.data.store.AppDataStore
import com.jdsdhp.cinepoliapp.data.store.AppDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): AppDataStore =
        AppDataStoreImpl(context)

}