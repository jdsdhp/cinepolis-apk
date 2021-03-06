package com.jdsdhp.cinepoliapp.data.store

import com.jdsdhp.cinepoliapp.data.store.model.Profile
import kotlinx.coroutines.flow.Flow

internal interface AppDataStore {

    val profileFlow: Flow<Profile?>

    suspend fun clearAll()

    suspend fun saveProfile(profile: Profile)

    fun provideProfile(): Profile?

    suspend fun saveAccessToken(token: String)

    suspend fun saveRefreshToken(refreshToken: String)

    fun provideAccessToken(): String?

    fun provideRefreshToken(): String?

}