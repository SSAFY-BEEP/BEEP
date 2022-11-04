package com.example.beep.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.beep.ui.navigation.NavGraph
import kotlinx.coroutines.launch
import com.example.beep.R

val dunggeunmoFont = FontFamily(
    Font(R.font.dunggeunmo)
)

val labdigitalFont = FontFamily(
    Font(R.font.labdigital)
)

val lanapixelFont = FontFamily(
    Font(R.font.lanapixel)
)


@Composable
fun LoginMainScreen() {
    val navController = rememberNavController()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        NavGraph(navController = navController)
        login_main()
    }
}

@Composable
fun login_main() {
    val vm = UserState.current
    val coroutineScope = rememberCoroutineScope()

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
            Button(onClick = {
                coroutineScope.launch {
                    vm.signIn(email= "1234", password = "1234")
                }
            }){
                Text(text = "로그인")
            }
            
            Button(onClick = {
                coroutineScope.launch {
                    vm.signOut()
                }
            }) {
                Text(text = "로그아웃")
            }

            Text("지금 생각나는 사람은?", fontFamily = pressFont)
            Text("Text", fontFamily = pressFont)
            Text("Text 텍스트 01234", fontFamily = dunggeunmoFont)
            Text("Text 텍스트 01234", fontFamily = labdigitalFont)
            Text("Text 텍스트 01234", fontFamily = lanapixelFont)
            
            Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "로그인")
            }

            Button(onClick = { }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "회원가입")
            }


        }
    }
}
