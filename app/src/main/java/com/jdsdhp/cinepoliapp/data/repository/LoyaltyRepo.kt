package com.jdsdhp.cinepoliapp.data.repository

import com.jdsdhp.cinepoliapp.data.api.ResponseResult
import com.jdsdhp.cinepoliapp.data.api.mappers.LoyaltyResponse

internal interface LoyaltyRepo {

    suspend fun fetchLoyalty(
        cardNumber: String,
        pin: String,
        countryCode: String,
    ): ResponseResult<LoyaltyResponse>

}