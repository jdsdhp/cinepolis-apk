package com.jdsdhp.cinepoliapp.data.api.mappers

import com.google.gson.annotations.SerializedName

data class LoyaltyBody(
    @SerializedName("card_number") val cardNumber: String,
    @SerializedName("pin") val pin: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("transaction_include") val transactionInclude: Boolean,
)

data class LoyaltyResponse(
    @SerializedName("balance_list") val balanceList: List<Balance>,
    @SerializedName("email") val email: String,
    @SerializedName("level") val level: Level,
    @SerializedName("name") val name: String,
    @SerializedName("pin") val pin: String?,
    @SerializedName("transactions") val transactions: List<Transaction>,
)

data class Transaction(
    @SerializedName("cinema") val cinema: String?,
    @SerializedName("date") val date: String,
    @SerializedName("message") val message: String?,
    @SerializedName("points") val points: Float,
)

data class Balance(
    @SerializedName("balance") val balance: Float,
    @SerializedName("key") val key: String,
    @SerializedName("message") val message: String,
    @SerializedName("name") val name: String,
)

data class Level(
    @SerializedName("advance_percent") val advancePercent: Float,
    @SerializedName("key") val key: String,
    @SerializedName("message") val message: String,
    @SerializedName("name") val name: String,
    @SerializedName("next_level") val nextLevel: String,
)
