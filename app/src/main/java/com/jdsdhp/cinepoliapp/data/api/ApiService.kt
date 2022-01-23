package com.jdsdhp.cinepoliapp.data.api

import com.jdsdhp.cinepoliapp.data.api.mappers.LoginResponse
import com.jdsdhp.cinepoliapp.data.store.model.Profile
import retrofit2.Response

internal interface ApiService {

    suspend fun login(
        clientId: String,
        clientSecret: String,
        countryCode: String,
        username: String,
        password: String,
        grantType: String
    ): Response<LoginResponse>

    suspend fun fetchProfile(): Response<Profile?>


}