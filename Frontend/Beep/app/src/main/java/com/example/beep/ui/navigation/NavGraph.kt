package com.example.beep.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
//import com.example.beep.ui.home.AddAddressSelfViewModel
import com.example.beep.ui.home.AddressViewModel
import com.example.beep.ui.login.JoinScreen
import com.example.beep.ui.login.LoginMainScreen
import com.example.beep.ui.login.LoginScreen
import com.example.beep.ui.login.UserViewModel

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
            val model: UserViewModel = hiltViewModel(it)
            LoginScreen()
        }
        composable(route = Screens.Join.route){
            val model: UserViewModel = hiltViewModel(it)
            JoinScreen()
        }
    }
}

sealed class Screens(val route: String) {
    object LoginMain : Screens("login_main")
    object Login: Screens("Login")
    object Join: Screens("Join")
}