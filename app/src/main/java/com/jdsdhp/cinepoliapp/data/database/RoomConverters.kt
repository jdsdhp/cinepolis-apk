package com.jdsdhp.cinepoliapp.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jdsdhp.cinepoliapp.data.api.mappers.Route
import com.jdsdhp.cinepoliapp.data.database.model.Media

class RoomConverters {

    private inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)

    @TypeConverter
    fun jsonToRoutes(json: String): List<Route> = Gson().fromJson<List<Route>>(json)

    @TypeConverter
    fun routesToJson(medias: List<Route>): String = Gson().toJson(medias)

    @TypeConverter
    fun jsonToMedias(json: String): List<Media> = Gson().fromJson<List<Media>>(json)

    @TypeConverter
    fun mediasToJson(medias: List<Media>): String = Gson().toJson(medias)

}