package com.jdsdhp.cinepoliapp.data.api

import com.jdsdhp.cinepoliapp.data.api.mappers.LoginResponse
import com.jdsdhp.cinepoliapp.data.store.model.Profile
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

internal interface RetrofitService : ApiService {

    @FormUrlEncoded
    @POST(URL_LOGIN)
    override suspend fun login(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("country_code") countryCode: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String,
    ): Response<LoginResponse>

    @GET(URL_PROFILE)
    override suspend fun fetchProfile(): Response<Profile?>

}