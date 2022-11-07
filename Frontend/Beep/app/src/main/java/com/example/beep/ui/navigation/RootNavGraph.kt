package com.example.beep.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.beep.ui.BeepApp


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RootNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController,
    startDestination = Graph.AUTHENTICATION) {
        composable(route = Graph.HOME) {
            BeepApp()
        }
    }
}

object Graph {
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_                                                                                                                                                                                                                                                                                                                                             graph"
}