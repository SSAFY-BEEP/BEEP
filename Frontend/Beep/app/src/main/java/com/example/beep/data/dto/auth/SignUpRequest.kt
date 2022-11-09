package com.example.beep.data.dto.auth

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("phoneNumber") val phoneNumber: String,
    @SerializedName("password") val password: String,
    @SerializedName("fcmToken") val fcmToken: String
)
