package com.example.beep.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.beep.ui.login.JoinScreen
import com.example.beep.ui.login.LoginMainScreen
import com.example.beep.ui.login.LoginScreen

@Composable
fun NavGraph (navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.LoginMain.route)
    {
        composable(route = Screens.LoginMain.route){
            LoginMainScreen()
        }
        composable(route = Screens.Login.route){
            LoginScreen()
        }
        composable(route = Screens.Join.route){
            JoinScreen()
        }
    }
}