package com.jdsdhp.cinepoliapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("code") val code: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("length") val length: String,
    @SerializedName("media") val media: List<Media>? = emptyList(),
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("position") val position: Int,
    @SerializedName("rating") val rating: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("synopsis") val synopsis: String,
)

data class Media(
    @SerializedName("code") val code: String,
    @SerializedName("resource") val resource: String,
    @SerializedName("type") val type: String,
)