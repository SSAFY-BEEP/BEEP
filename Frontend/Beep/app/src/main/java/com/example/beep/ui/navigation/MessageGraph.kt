package com.example.beep.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.beep.ui.message.MessageScreen
import com.example.beep.ui.message.MessageViewModel
import com.example.beep.ui.message.RecordVoiceScreen

@RequiresApi(Build.VERSION_CODES.S)
fun NavGraphBuilder.messageGraph(navController: NavController) {
    navigation(startDestination = "chat", route="message") {
        composable("chat") {
            val model: MessageViewModel = hiltViewModel(it)
            MessageScreen(model) { navController.navigate("recordVoice") }
        }
        composable("recordVoice") {
            RecordVoiceScreen()
        }
    }

}