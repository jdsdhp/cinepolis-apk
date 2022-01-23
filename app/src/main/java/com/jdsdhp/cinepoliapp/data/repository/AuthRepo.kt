package com.jdsdhp.cinepoliapp.data.repository

import com.jdsdhp.cinepoliapp.data.api.ResponseResult

internal interface AuthRepo {

    suspend fun sendLogin(email: String, password: String): ResponseResult<Any>

}