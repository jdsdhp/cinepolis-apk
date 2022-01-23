package com.jdsdhp.cinepoliapp.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jdsdhp.cinepoliapp.data.database.model.Media
import com.jdsdhp.cinepoliapp.data.database.model.Sizes

class RoomConverters {

    private inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)

    @TypeConverter
    fun jsonToSizes(json: String): Sizes = Gson().fromJson<Sizes>(json)

    @TypeConverter
    fun sizesToJson(sizes: Sizes): String = Gson().toJson(sizes)

    @TypeConverter
    fun jsonToMedias(json: String): List<Media> = Gson().fromJson<List<Media>>(json)

    @TypeConverter
    fun mediasToJson(medias: List<Media>): String = Gson().toJson(medias)

}