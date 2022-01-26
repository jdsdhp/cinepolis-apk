package com.jdsdhp.cinepoliapp.data.api.mappers

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.jdsdhp.cinepoliapp.data.database.model.Media
import com.jdsdhp.cinepoliapp.data.database.model.Movie
import kotlinx.parcelize.Parcelize

@Keep
data class MoviesWrapper(
    @SerializedName("movies") val movieRes: List<MovieRes>,
    @SerializedName("routes") val routeRes: List<Route>
)

@Keep
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

@Keep
@Parcelize
data class Route(
    @SerializedName("code") val code: String,
    @SerializedName("sizes") val sizesRes: SizesRes,
) : Parcelable

@Keep
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

@Keep
@Parcelize
data class SizesRes(
    @SerializedName("large") val large: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("small") val small: String,
):Parcelable

enum class SizeType { LARGE, MEDIUM, SMALL }