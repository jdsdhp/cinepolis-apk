package com.jdsdhp.cinepoliapp.data.api

import com.jdsdhp.cinepoliapp.data.api.mappers.Login
import com.jdsdhp.cinepoliapp.data.api.mappers.LoginResponse
import retrofit2.Response

internal interface ApiService {

    suspend fun login(body: Login): Response<LoginResponse>

}