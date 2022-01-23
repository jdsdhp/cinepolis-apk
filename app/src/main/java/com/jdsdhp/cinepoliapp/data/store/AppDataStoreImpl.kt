package com.jdsdhp.cinepoliapp.data.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.jdsdhp.cinepoliapp.data.store.model.Profile
import com.jdsdhp.cinepoliapp.data.store.model.toJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

internal class AppDataStoreImpl(private val context: Context) : AppDataStore {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "store")

    private object Keys {
        val PROFILE_KEY = stringPreferencesKey("profile_key")
        val TOKEN_KEY = stringPreferencesKey("token_key")
        val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token_key")
    }

    override val profileFlow: Flow<Profile?> =
        context.dataStore.data.map { it[Keys.PROFILE_KEY]?.let { p -> Profile.fromJson(p) } }

    override suspend fun clearAll() {
        context.dataStore.edit { it.clear() }
    }

    override suspend fun saveProfile(profile: Profile) {
        context.dataStore.edit { it[Keys.PROFILE_KEY] = profile.toJson() }
    }

    override fun provideProfile(): Profile? = runBlocking {
        context.dataStore.data.firstOrNull()?.get(Keys.PROFILE_KEY)?.let { Profile.fromJson(it) }
    }

    override suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { it[Keys.TOKEN_KEY] = token }
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        context.dataStore.edit { it[Keys.REFRESH_TOKEN_KEY] = refreshToken }
    }

    override fun provideAccessToken(): String? = runBlocking {
        context.dataStore.data.firstOrNull()?.get(Keys.TOKEN_KEY)
    }

    override fun provideRefreshToken(): String? = runBlocking {
        context.dataStore.data.firstOrNull()?.get(Keys.REFRESH_TOKEN_KEY)
    }

}