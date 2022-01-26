package com.jdsdhp.cinepoliapp.data.database.model

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.jdsdhp.cinepoliapp.data.api.mappers.Route
import com.jdsdhp.cinepoliapp.data.api.mappers.SizeType
import com.jdsdhp.cinepoliapp.data.api.mappers.SizesRes
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey @SerializedName("id") val id: Int,
    @SerializedName("genre") val genre: String,
    @SerializedName("length") val length: String,
    @SerializedName("media") val medias: List<Media>? = emptyList(),
    @SerializedName("name") val name: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("synopsis") val synopsis: String,
) : Parcelable

fun Movie.getResourceUrl(resourceType: ResourceType, sizeType: SizeType): String? {
    val media: Media? = this.medias?.find { it.code == resourceType.code }
    media?.let { m ->
        val resource: String = m.resource
        val sizes: SizesRes? = m.routes.find { it.code == resourceType.code }?.sizesRes
        val resourcePath: String? = when (sizeType) {
            SizeType.LARGE -> sizes?.large
            SizeType.MEDIUM -> sizes?.medium
            SizeType.SMALL -> sizes?.small
        }
        return "$resourcePath$resource"
    }
    return null
}

@Parcelize
data class Media(
    @SerializedName("code") val code: String,
    @SerializedName("resource") val resource: String,
    @SerializedName("type") val type: String,
    @SerializedName("routes") val routes: List<Route>,
) : Parcelable

enum class ResourceType(val code: String) {
    POSTER("poster"),
    BACKGROUND_SYNOPSIS("background_synopsis"),
    TRAILER("trailer_mp4"),
    POSTER_HORIZONTAL("poster_horizontal")
}