package com.example.beep.ui.navigation

sealed class Screens(val route: String) {
    object LoginMain : Screens("login_main")
    object Login: Screens("login")
    object Join: Screens("Join")
}