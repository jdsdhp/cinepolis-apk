package com.jdsdhp.cinepoliapp.data.repository

import com.jdsdhp.cinepoliapp.BuildConfig
import com.jdsdhp.cinepoliapp.data.api.ApiService
import com.jdsdhp.cinepoliapp.data.api.RequestHandler
import com.jdsdhp.cinepoliapp.data.api.ResponseResult
import com.jdsdhp.cinepoliapp.data.store.AppDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class AuthRepoImpl internal constructor(
    private val service: ApiService,
    private val requestHandler: RequestHandler,
    private val dataStore: AppDataStore,
) : AuthRepo {

    override suspend fun sendLogin(email: String, password: String): ResponseResult<Any> =
        withContext(Dispatchers.IO) {
            val loginRes = requestHandler.safeApiCall {
                service.login(
                    clientId = BuildConfig.CLIENT_ID,
                    clientSecret = BuildConfig.CLIENT_SECRET,
                    countryCode = "MX",
                    username = email,
                    password = password,
                    grantType = "password",
                )
            }
            if (loginRes is ResponseResult.Success
                && loginRes.data != null
            ) {
                dataStore.saveAccessToken(loginRes.data.accessToken)
                dataStore.saveRefreshToken(loginRes.data.refreshToken)

                val profileRes = requestHandler.safeApiCall { service.fetchProfile() }
                if (profileRes is ResponseResult.Success && profileRes.data != null
                ) {
                    dataStore.saveProfile(profileRes.data)
                } else {
                    return@withContext if (profileRes is ResponseResult.Error) profileRes
                    else ResponseResult.Error.DEFAULT
                }

                return@withContext ResponseResult.Success()
            } else {
                return@withContext if (loginRes is ResponseResult.Error) loginRes
                else ResponseResult.Error.DEFAULT
            }
        }

}