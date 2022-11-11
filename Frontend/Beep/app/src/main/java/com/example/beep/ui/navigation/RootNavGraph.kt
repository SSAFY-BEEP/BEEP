package com.example.beep.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.beep.di.MainApplication
import com.example.beep.ui.BeepApp
import com.example.beep.ui.login.*

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RootNavGraph(navController: NavHostController) {
    val token = MainApplication.sharedPreferencesUtil.getToken()
    var isLoggedDestination = "login_main_graph"

    if (token != null) {
        if (token.isNotBlank()) {
            isLoggedDestination = "beep_graph"
        }
    }
    NavHost(navController = navController, startDestination = isLoggedDestination) {
        composable("beep_graph") {
            BeepApp()
        }
        authNavGraph(navController)
    }
}

fun NavGraphBuilder.authNavGraph(navController: NavController) {
    navigation(startDestination = "login_main_graph", route="login_main_graph") {
        composable("login_main_graph"){
            MainButtonScreen()
        }
        composable("login_graph"){
            val model: UserViewModel = hiltViewModel(it)
            LoginScreen()
        }
        composable("join_graph"){
            val model: UserViewModel = hiltViewModel(it)
            JoinScreen()
        }
    }
}