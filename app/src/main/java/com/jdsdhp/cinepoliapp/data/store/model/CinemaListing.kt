package com.jdsdhp.cinepoliapp.data.store.model

import com.google.gson.annotations.SerializedName

data class CinemaListing(
    @SerializedName("movies") val movies: List<Movie>,
    @SerializedName("routes") val routes: List<Route>
)

data class Media(
    @SerializedName("code") val code: String,
    @SerializedName("resource") val resource: String,
    @SerializedName("type") val type: String,
)

data class Movie(
    @SerializedName("categories") val categories: List<String>,
    @SerializedName("cinemas") val cinemas: List<Int>,
    @SerializedName("code") val code: String,
    @SerializedName("genre") val genre: String,
    @SerializedName("id") val id: Int,
    @SerializedName("length") val length: String,
    @SerializedName("media") val media: List<Media>,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("position") val position: Int,
    @SerializedName("rating") val rating: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("synopsis") val synopsis: String,
)

data class Route(
    @SerializedName("code") val code: String,
    @SerializedName("sizes") val sizes: Sizes,
)

data class Sizes(
    @SerializedName("large") val large: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("small") val small: String,
)