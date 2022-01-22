package com.jdsdhp.cinepoliapp.data.store.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("address") val address: String,
    @SerializedName("city_id") val cityId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lng: String,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("position") val position: Int,
    @SerializedName("settings") val settings: Settings,
    @SerializedName("uris") val uris: String,
    @SerializedName("vista_id") val vistaId: String,
)

data class Settings(
    @SerializedName("cs_merchant_id") val csMerchantId: String,
    @SerializedName("is_special_prices") val isSpecialPrices: Boolean,
    @SerializedName("type_food_sales") val typeFoodSales: String,
    @SerializedName("vco_merchant_id") val vcoMerchantId: String,
)