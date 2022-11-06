package com.example.beep.data.dto.auth

data class SignUpResponse(
    val alarm: Int,
    val authority: String,
    val engrave: String,
    val fcmToken: String,
    val font: Int,
    val id: Int,
    val introduceAudio: String,
    val password: String,
    val phoneNumber: String,
    val theme: Int
)