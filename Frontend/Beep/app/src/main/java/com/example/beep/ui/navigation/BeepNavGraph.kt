package com.example.beep.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.beep.ui.home.HomeScreen
import com.example.beep.ui.login.LoginMainScreen
import com.example.beep.ui.message.MessageScreen
import com.example.beep.ui.message.MessageViewModel
import com.example.beep.ui.mypage.MyPageScreen
import com.example.beep.ui.navigation.messageGraph

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BeepNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        messageGraph(navController)
        composable("settings") {
            MyPageScreen()
        }
        composable("login_main") {
            LoginMainScreen()
        }
    }
}