package com.example.beep.ui.login

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.beep.di.MainApplication
import kotlinx.coroutines.delay

@Composable
fun MainButtonScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(visible) {
        Log.d("launchEffect 실행", "$visible")
        if (!visible) {
            delay(1000)
            visible = true
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center

    ) {
        Column(
            Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "BEEP", modifier = Modifier)
        }
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + expandVertically(
                animationSpec = tween(1000, 1000)
            ),
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(48.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = { navController.navigate("login_graph") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "로그인")
                }

                Button(
                    onClick = { navController.navigate("join_graph") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "회원가입")
                }
            }
        }
    }
}