package com.example.beep.data.dto.auth

data class NewPasswordRequest(
    val password: String,
    val phoneNumber: String
)