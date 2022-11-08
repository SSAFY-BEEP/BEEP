package com.example.beep.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.beep.ui.home.HomeScreen
import com.example.beep.ui.login.LoginMainScreen
import com.example.beep.ui.message.MessageScreen
import com.example.beep.ui.message.MessageViewModel
import com.example.beep.ui.message.RecordVoiceScreen
import com.example.beep.ui.mypage.*

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BeepNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        messageGraph(navController)
        myPageGraph(navController)
        composable("login_main") {
            LoginMainScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
fun NavGraphBuilder.messageGraph(navController: NavController) {
    navigation(startDestination = "messageList", route = "message") {
        composable("messageList") {
            val model: MessageViewModel = hiltViewModel(it)
            MessageScreen(model) { navController.navigate("recordVoice") }
        }
        composable("recordVoice") {
            RecordVoiceScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
fun NavGraphBuilder.myPageGraph(navController: NavController) {
    navigation(startDestination = "myPage", route = "settings") {

        composable("myPage") {
            MyPageScreen() { route: String -> navController.navigate(route) }
        }
        composable("contactPreset") {
            val model: MyPageViewModel = hiltViewModel(it)
            ContactPresetScreen(viewModel = model)
        }
        composable("messagePreset") {
            val model: MyPageViewModel = hiltViewModel(it)
            MessagePresetScreen(viewModel = model)
        }
        composable("greetingPreset") {
            val model: IntroduceViewModel = hiltViewModel(it)
            IntroduceScreen(viewModel = model)
        }
        composable("colorSetting") {
            ColorSettingScreen()
        }
        composable("engravingSetting") {
            EngravingSettingScreen()
        }
        composable("fontSetting") {
            FontSettingScreen()
        }
        composable("passwordChange") {
            val model: MyPageViewModel = hiltViewModel(it)
            PasswordChangeScreen(viewModel = model)
        }
        composable("recordIntroduce") {
            RecordVoiceScreen()
        }
    }
}