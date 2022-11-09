package com.example.beep.ui.login

sealed class LoginFormEvent {
    data class LoginPhoneNumberChanged(val loginPhoneNumber: String) : LoginFormEvent()
    data class LoginPasswordChanged(val loginPassword: String) : LoginFormEvent()

    object Submit: LoginFormEvent()
}