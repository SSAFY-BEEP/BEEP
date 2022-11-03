package com.example.beep.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.beep.ui.navigation.NavGraph
import com.example.beep.ui.navigation.Screens

@Composable
fun LoginMainScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        login_main()
    }
}

@Composable
fun login_main() {
    val navController = rememberNavController()
    NavGraph(navController = navController)
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

            Text(text = "지금 생각나는 사람은?")

            Button(onClick = { navController.navigate(Screens.Login.route)}, modifier = Modifier.fillMaxWidth()) {
                Text(text = "로그인")
            }

            Button(onClick = { navController.navigate(Screens.Join.route)}, modifier = Modifier.fillMaxWidth()) {
                Text(text = "회원가입")
            }


        }
    }
}