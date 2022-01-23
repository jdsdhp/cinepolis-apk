package com.jdsdhp.cinepoliapp.data.api

import com.jdsdhp.cinepoliapp.data.api.mappers.Login
import com.jdsdhp.cinepoliapp.data.api.mappers.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface RetrofitService : ApiService {

    @POST(URL_LOGIN)
    override suspend fun login(@Body body: Login): Response<LoginResponse>

}