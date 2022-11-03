package com.example.beep.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.beep.ui.login.JoinScreen
import com.example.beep.ui.login.LoginScreen

@Composable
fun NavGraph (navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route)
    {
        composable(route = Screens.Login.route){
            LoginScreen(navController)
        }
        composable(route = Screens.Join.route){
            JoinScreen(navController)
        }
    }
}