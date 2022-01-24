package com.jdsdhp.cinepoliapp.data.api.mappers

import com.google.gson.annotations.SerializedName
import com.jdsdhp.cinepoliapp.data.database.model.Media
import com.jdsdhp.cinepoliapp.data.database.model.Movie

data class MoviesWrapper(
    @SerializedName("movies") val movieRes: List<MovieRes>,
    @SerializedName("routes") val routeRes: List<Route>
)

data class MovieRes(
    @SerializedName("id") val id: Int,
    @SerializedName("categories") val categories: List<String>,
    @SerializedName("cinemas") val cinemas: List<Int>,
    @SerializedName("genre") val genre: String,
    @SerializedName("length") val length: String,
    @SerializedName("media") val mediaRes: List<MediaRes>,
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("synopsis") val synopsis: String,
)

fun MovieRes.toMovie(routes: List<Route>) = Movie(
    id = this.id,
    genre = this.genre,
    length = this.length,
    medias = this.mediaRes.toMedias(routes),
    name = this.name,
    rating = this.rating,
    synopsis = this.synopsis,
)

data class Route(
    @SerializedName("code") val code: String,
    @SerializedName("sizes") val sizesRes: SizesRes,
)

data class MediaRes(
    @SerializedName("code") val code: String,
    @SerializedName("resource") val resource: String,
    @SerializedName("type") val type: String,
)

fun List<MediaRes>.toMedias(routes: List<Route>): List<Media> = this.map {
    Media(
        code = it.code,
        resource = it.resource,
        type = it.type,
        routes = routes,
    )
}

data class SizesRes(
    @SerializedName("large") val large: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("small") val small: String,
)

enum class SizeType { LARGE, MEDIUM, SMALL }