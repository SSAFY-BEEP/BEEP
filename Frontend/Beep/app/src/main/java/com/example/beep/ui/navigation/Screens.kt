package com.example.beep.ui.navigation

sealed class Screens(val route: String) {
    object Login: Screens("login")
    object Join: Screens("Join")
}