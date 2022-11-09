package com.example.beep.ui.login

data class LoginState(
    val loginPhoneNumber: String = "",
    val loginPhoneNumberError: String? = null,
    val loginPassword: String = "",
    val loginPasswordError: String? = null
)
