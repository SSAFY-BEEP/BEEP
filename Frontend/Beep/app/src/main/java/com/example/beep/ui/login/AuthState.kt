package com.example.beep.ui.login

data class AuthState(
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val passwordCheck: String = "",
    val passwordCheckError: String? = null,
    val acceptedTerms: Boolean = false,
    val termError: String? = null,

)
