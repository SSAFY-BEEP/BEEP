package com.example.beep.data.dto.auth

data class SignUpRequest(
        val phoneNumber: String,
        val password: String,
        val fcmToken: String
)
