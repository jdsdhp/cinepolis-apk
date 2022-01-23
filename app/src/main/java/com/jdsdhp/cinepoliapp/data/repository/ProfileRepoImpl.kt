package com.jdsdhp.cinepoliapp.data.repository

import com.jdsdhp.cinepoliapp.data.api.ApiService
import com.jdsdhp.cinepoliapp.data.api.RequestHandler
import com.jdsdhp.cinepoliapp.data.api.ResponseResult
import com.jdsdhp.cinepoliapp.data.store.AppDataStore
import com.jdsdhp.cinepoliapp.data.store.model.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class ProfileRepoImpl internal constructor(
    private val service: ApiService,
    private val requestHandler: RequestHandler,
    private val dataStore: AppDataStore,
) : ProfileRepo {

    override val profileFlow: Flow<Profile?> = dataStore.profileFlow

    override suspend fun fetchProfile() = withContext(Dispatchers.IO) {
        val profileRes = requestHandler.safeApiCall { service.fetchProfile() }

        if (profileRes is ResponseResult.Success && profileRes.data != null) {
            dataStore.saveProfile(profileRes.data)
            return@withContext ResponseResult.Success()
        } else {
            return@withContext if (profileRes is ResponseResult.Error) profileRes
            else ResponseResult.Error.DEFAULT
        }
    }

}