package com.example.beep.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.beep.R
import com.example.beep.ui.home.*
import com.example.beep.ui.login.MainButtonScreen
import com.example.beep.ui.message.MessageScreen
import com.example.beep.ui.message.MessageViewModel
import com.example.beep.ui.mypage.*
import com.example.beep.ui.mypage.introduce.IntroduceScreen
import com.example.beep.ui.mypage.introduce.IntroduceViewModel
import com.example.beep.ui.mypage.introduce.RecordVoiceViewModel
import com.example.beep.ui.savedmessage.SavedMessageScreen
import com.example.beep.ui.savedmessage.SavedMessageViewModel
import com.example.beep.ui.theme.BACKGROUND_WHITE

val galmurinineFont = FontFamily(
    Font(R.font.galmurinine)
)

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BeepNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController,
        startDestination = "home",
        modifier = Modifier.imePadding().padding(0.dp,0.dp,0.dp,56.dp).background(BACKGROUND_WHITE)) {
        composable("home") {
            val model: AddressViewModel = hiltViewModel(it)
            val postAddress: AddressPostSelfViewModel = hiltViewModel(it)
            val deleteAddress: AddressDeleteViewModel = hiltViewModel(it)
            val patchAddress: AddressPatchViewModel = hiltViewModel(it)
            val homeModel: HomeViewModel = hiltViewModel(it)
            val presetViewModel: PresetViewModel = hiltViewModel(it)
            val keyboardViewModel: KeyboardViewModel = hiltViewModel(it)
            HomeScreen()
        }
        messageGraph(navController)
        myPageGraph(navController)
        composable("login_main_graph") {
            MainButtonScreen(navController=navController)
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
        composable("savedMessage") {
            val model: SavedMessageViewModel = hiltViewModel(it)
            SavedMessageScreen(navigateTo = { route: String -> navController.navigate(route) })
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
            val presetViewModel: PresetViewModel = hiltViewModel(it)
            ContactPresetScreen(viewModel = model)
        }
        composable("messagePreset") {
            val model: MyPageViewModel = hiltViewModel(it)
            val presetViewModel: PresetViewModel = hiltViewModel(it)
            MessagePresetScreen(viewModel = model)
        }
        composable("greetingPreset") {
            val model: IntroduceViewModel = hiltViewModel(it)
            val model2: RecordVoiceViewModel = hiltViewModel(it)
            IntroduceScreen(viewModel = model)
        }
        composable("colorSetting") {
            val model: MyPageViewModel = hiltViewModel(it)
            ColorSettingScreen(model = model)
        }
        composable("engravingSetting") {
            val model: MyPageViewModel = hiltViewModel(it)
            EngravingSettingScreen(model = model)
        }
        composable("fontSetting") {
            val model: MyPageViewModel = hiltViewModel(it)
            FontSettingScreen(model = model)
        }
        composable("passwordChange") {
            val model: MyPageViewModel = hiltViewModel(it)
            PasswordChangeScreen(viewModel = model)
        }
    }
}