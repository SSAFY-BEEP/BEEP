@file:Suppress("UNUSED_EXPRESSION")

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.beep.data.LoginNavItem
import com.example.beep.di.MainApplication
import com.example.beep.ui.mypage.*
import kotlinx.coroutines.launch

@Composable
fun LoginMainScreen() {
    val navController = rememberNavController()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { MainApplication.sharedPreferencesUtil.deleteToken() }) {
                Text(text = "로그아웃")
            }

            Button(onClick = { }) {
                Text(text = "회원탈퇴")
            }

            Button(onClick = { } ) {
                Text(text = "비밀번호 변경")
            }

        }
    }
}
