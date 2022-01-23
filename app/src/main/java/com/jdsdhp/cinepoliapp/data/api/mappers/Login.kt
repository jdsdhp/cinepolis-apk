package com.jdsdhp.cinepoliapp.data.api.mappers

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("client_id") val clientId: String,
    @SerializedName("client_secret") val clientSecret: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("grant_type") val grantType: String,
    @SerializedName("password") val password: String,
    @SerializedName("username") val username: String,
)

data class LoginResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("as:client_id") val asClientId: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName(".expires") val expires: String,
    @SerializedName("expires_in") val expiresIn: Int,
    @SerializedName(".issued") val issued: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("username") val username: String,
)
