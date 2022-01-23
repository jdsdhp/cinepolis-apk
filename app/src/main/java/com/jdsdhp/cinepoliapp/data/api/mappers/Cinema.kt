package com.jdsdhp.cinepoliapp.data.api.mappers

import com.google.gson.annotations.SerializedName

data class MoviesWrapper(
    @SerializedName("movies") val movieRes: List<MovieRes>,
    @SerializedName("routes") val routeRes: List<RouteRes>
)

data class MovieRes(
    @SerializedName("categories") val categories: List<String>,
    @SerializedName("cinemas") val cinemas: List<Int>,
    @SerializedName("code") val code: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("id") val id: Int,
    @SerializedName("length") val length: String,
    @SerializedName("media") val mediaRes: List<MediaRes>,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("position") val position: Int,
    @SerializedName("rating") val rating: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("synopsis") val synopsis: String,
)

data class RouteRes(
    @SerializedName("code") val code: String,
    @SerializedName("sizes") val sizesRes: SizesRes,
)

data class MediaRes(
    @SerializedName("code") val code: String,
    @SerializedName("resource") val resource: String,
    @SerializedName("type") val type: String,
)

data class SizesRes(
    @SerializedName("large") val large: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("small") val small: String,
)