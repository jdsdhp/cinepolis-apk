package com.jdsdhp.cinepoliapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "routes")
data class Route(
    @PrimaryKey @SerializedName("code") val code: String,
    @SerializedName("sizes") val sizes: Sizes,
)

data class Sizes(
    @SerializedName("large") val large: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("small") val small: String,
)