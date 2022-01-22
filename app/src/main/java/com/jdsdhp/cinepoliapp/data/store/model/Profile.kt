package com.jdsdhp.cinepoliapp.data.store.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("card_number") val cardNumber: String,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("phone_number") val phoneNumber: String,
    @SerializedName("profile_picture") val profilePicture: String,
) {
    companion object {
        fun fromJson(jsonProfile: String) = Gson().fromJson(jsonProfile, Profile::class.java)
    }
}

fun Profile.toJson(): String = Gson().toJson(this)