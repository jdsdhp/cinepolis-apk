package com.jdsdhp.cinepoliapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "locations")
data class Location(
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("address") val address: String,
    @SerializedName("city_id") val cityId: Int,
    @SerializedName("lat") val lat: String,
    @SerializedName("lng") val lng: String,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("position") val position: Int,
    @SerializedName("uris") val uris: String,
    @SerializedName("vista_id") val vistaId: String,
    //Settings
    @SerializedName("cs_merchant_id") val csMerchantId: String,
    @SerializedName("is_special_prices") val isSpecialPrices: Boolean,
    @SerializedName("type_food_sales") val typeFoodSales: String,
    @SerializedName("vco_merchant_id") val vcoMerchantId: String,
)