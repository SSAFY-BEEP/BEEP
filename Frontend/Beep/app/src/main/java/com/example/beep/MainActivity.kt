package com.example.beep

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.beep.ui.BeepApp
import com.example.beep.ui.login.UserState
import com.example.beep.ui.login.UserStateViewModel
import com.example.beep.ui.login.login_main
import com.example.beep.ui.navigation.NavGraph
import com.example.beep.ui.theme.BeepTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication: Application(){
    override fun onCreate() {
        super.onCreate()
    }
}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userState by viewModels<UserStateViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeepTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    CompositionLocalProvider(UserState provides userState) {
                        val vm = UserState.current
                        if (vm.isLoggedIn) {
                            BeepApp()
                        } else {
                            login_main()
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BeepTheme {
    }
}