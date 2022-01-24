package com.jdsdhp.cinepoliapp.data.repository

import com.jdsdhp.cinepoliapp.data.api.ApiService
import com.jdsdhp.cinepoliapp.data.api.RequestHandler
import com.jdsdhp.cinepoliapp.data.api.ResponseResult
import com.jdsdhp.cinepoliapp.data.api.mappers.LoyaltyBody
import com.jdsdhp.cinepoliapp.data.api.mappers.LoyaltyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class LoyaltyRepoImpl internal constructor(
    private val service: ApiService,
    private val requestHandler: RequestHandler,
) : LoyaltyRepo {

    override suspend fun fetchLoyalty(
        cardNumber: String,
        pin: String,
        countryCode: String,
    ): ResponseResult<LoyaltyResponse> = withContext(Dispatchers.IO) {
        requestHandler.safeApiCall {
            service.fetchLoyalty(
                LoyaltyBody(
                    cardNumber = cardNumber,
                    pin = pin,
                    countryCode = countryCode,
                    transactionInclude = true,
                )
            )
        }
    }

}