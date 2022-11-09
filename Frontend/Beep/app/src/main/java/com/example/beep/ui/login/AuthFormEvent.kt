package com.example.beep.ui.login

sealed class AuthFormEvent {
    data class PhoneNumberChanged(val phoneNumber: String) : AuthFormEvent()
    data class PasswordChanged(val password: String) : AuthFormEvent()
    data class PasswordCheckChanged(val passwordCheck: String) : AuthFormEvent()
    data class AcceptTerms(val isAccepted: Boolean) : AuthFormEvent()


    object Submit: AuthFormEvent()
}
