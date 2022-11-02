package com.example.beep.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.beep.ui.home.HomeScreen
import com.example.beep.ui.message.MessageScreen
import com.example.beep.ui.mypage.MyPageScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BeepNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        composable("chat") {
            MessageScreen()
        }
        composable("settings") {
            MyPageScreen()
        }
    }
}