package com.jdsdhp.cinepoliapp.di

import com.jdsdhp.cinepoliapp.data.api.ApiService
import com.jdsdhp.cinepoliapp.data.api.RequestHandler
import com.jdsdhp.cinepoliapp.data.repository.AuthRepo
import com.jdsdhp.cinepoliapp.data.repository.AuthRepoImpl
import com.jdsdhp.cinepoliapp.data.repository.ProfileRepo
import com.jdsdhp.cinepoliapp.data.repository.ProfileRepoImpl
import com.jdsdhp.cinepoliapp.data.store.AppDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        service: ApiService,
        requestHandler: RequestHandler,
        dataStore: AppDataStore,
    ): AuthRepo = AuthRepoImpl(
        service = service,
        requestHandler = requestHandler,
        dataStore = dataStore,
    )

    @Singleton
    @Provides
    fun provideProfileRepository(
        service: ApiService,
        requestHandler: RequestHandler,
        dataStore: AppDataStore,
    ): ProfileRepo = ProfileRepoImpl(
        service = service,
        requestHandler = requestHandler,
        dataStore = dataStore,
    )

}