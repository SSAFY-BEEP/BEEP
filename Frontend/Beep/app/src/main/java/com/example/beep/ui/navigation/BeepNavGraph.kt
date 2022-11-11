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
import com.example.beep.ui.home.*
import com.example.beep.ui.login.JoinScreen
import com.example.beep.ui.home.HomeViewModel
import com.example.beep.ui.login.LoginMainScreen
import com.example.beep.ui.login.UserViewModel
import com.example.beep.ui.message.MessageScreen
import com.example.beep.ui.message.MessageViewModel
import com.example.beep.ui.message.RecordVoiceScreen
import com.example.beep.ui.message.RecordVoiceViewModel
import com.example.beep.ui.mypage.*
import com.example.beep.ui.savedmessage.SavedMessageScreen
import com.example.beep.ui.savedmessage.SavedMessageViewModel

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BeepNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            val model: AddressViewModel = hiltViewModel(it)
            val postAddress: AddressPostSelfViewModel = hiltViewModel(it)
            val deleteAddress: AddressDeleteViewModel = hiltViewModel(it)
            val patchAddress: AddressPatchViewModel = hiltViewModel(it)
            val homeModel: HomeViewModel = hiltViewModel(it)
            val presetViewModel: PresetViewModel = hiltViewModel(it)
            HomeScreen()
        }
        messageGraph(navController)
        myPageGraph(navController)
        composable("login_main") {
            val model: UserViewModel = hiltViewModel(it)
            LoginMainScreen()
        }
        composable("savedMessage") {
            val model: SavedMessageViewModel = hiltViewModel(it)
            SavedMessageScreen(navigateTo = { route: String -> navController.navigate(route) })
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
fun NavGraphBuilder.messageGraph(navController: NavController) {
    navigation(startDestination = "messageList", route = "message") {
        composable("messageList") {
            val model: MessageViewModel = hiltViewModel(it)
            MessageScreen(model) { route: String -> navController.navigate(route) }
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
            val model2: RecordVoiceViewModel = hiltViewModel(it)
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
    }
}