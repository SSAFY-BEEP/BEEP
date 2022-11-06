package com.example.beep.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.beep.ui.mypage.*
import com.example.beep.ui.navigation.NavGraph
import kotlinx.coroutines.launch

@Composable
fun LoginMainScreen() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        JoinScreen()
    }
}

@Composable
fun login_main() {

    Box(
        modifier = Modifier
                .fillMaxSize()
                .background(
                        color = Color(0XFFF5F8FF)
                )
    ) {

        Column(
                Modifier
                        .fillMaxSize()
                        .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            
            Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "로그인")
            }

            Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "회원가입")
            }


        }
    }
}