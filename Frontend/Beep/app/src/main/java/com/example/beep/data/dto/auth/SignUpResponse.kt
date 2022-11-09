package com.example.beep.data.dto.auth

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("alarm") val alarm: Int,
    @SerializedName("authority") val authority: String,
    @SerializedName("engrave") val engrave: String,
    @SerializedName("fcmToken") val fcmToken: String,
    @SerializedName("font") val font: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("introduceAudio") val introduceAudio: String,
    @SerializedName("password") val password: String,
    @SerializedName("PhoneNumber") val phoneNumber: String,
    @SerializedName("theme") val theme: Int
)