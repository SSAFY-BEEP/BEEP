package com.example.beep.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.beep.di.MainApplication

@Composable
fun MainButtonScreen(navController: NavController) {
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

            Button(onClick = { navController.navigate("login_graph") }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "로그인")
            }

            Button(onClick = { navController.navigate("join_graph")}, modifier = Modifier.fillMaxWidth()) {
                Text(text = "회원가입")
            }
        }
    }
}