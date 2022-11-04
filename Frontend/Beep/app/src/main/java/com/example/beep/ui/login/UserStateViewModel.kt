package com.example.beep.ui.login

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import kotlinx.coroutines.delay

class UserStateViewModel : ViewModel() {
    var isLoggedIn by mutableStateOf(false)
    var isBusy by mutableStateOf(false)

    suspend fun signIn(email: String, password: String) {
        isBusy = true
        delay(2000)
        isLoggedIn = true
        isBusy = false
    }

    suspend fun signOut() {
        isBusy = true
        delay(2000)
        isLoggedIn = false
        isBusy = false
    }
}

val UserState = compositionLocalOf<UserStateViewModel> {error("User State Context Not Found") }