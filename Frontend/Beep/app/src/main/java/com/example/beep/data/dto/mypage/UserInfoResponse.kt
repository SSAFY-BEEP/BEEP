package com.example.beep.data.dto.mypage

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("id") val id : Long,
    @SerializedName("phoneNumber") val phoneNumber : String,
    @SerializedName("password") val password : String,
    @SerializedName("introduceAudio") val introduceAudio : String,
    @SerializedName("engrave") val engrave : String,
    @SerializedName("theme") val theme : String,
    @SerializedName("font") val font : String,
    @SerializedName("alarm") val alarm : String,
    @SerializedName("authority") val authority : String,
    @SerializedName("fcmToken") val fcmToken : String,
)
