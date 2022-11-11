package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


enum class MyPageState {
    Main, Preset, Style, Member
}

@Composable
fun MyPageScreen(onClickMenu: (String) -> Unit) {
    var currentStage by remember { mutableStateOf(MyPageState.Main) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (currentStage) {
            MyPageState.Main -> {
                MyPageMain(
                    onClickMenu = onClickMenu,
                    changeMenuState = { newState: MyPageState -> currentStage = newState })
            }
            MyPageState.Preset -> {
                MyPagePreset(onClickMenu)
            }
            MyPageState.Style -> {
                MyPageStyle(onClickMenu)
            }
            MyPageState.Member -> {
                MyPageMember(onClickMenu)
            }
        }
    }
}

@Composable
fun MyPageMember(onClickMenu: (String) -> Unit) {
    Column() {
        TextButton(onClick = { onClickMenu("passwordChange") }) {
            Text("비밀번호 변경")
        }
        TextButton(onClick = { /* 로그아웃 메서드 */ }) {
            Text("로그아웃")
        }
        TextButton(onClick = { /* 회원 탈퇴 메서드 */ }) {
            Text(text = "회원 탈퇴", color = Color.Red)
        }
    }
}

@Composable
fun MyPageStyle(onClickMenu: (String) -> Unit) {
    Column() {
        TextButton(onClick = { onClickMenu("colorSetting") }) {
            Text("테마색 변경")
        }
        TextButton(onClick = { onClickMenu("engravingSetting") }) {
            Text("각인 변경")
        }
        TextButton(onClick = { onClickMenu("fontSetting") }) {
            Text("폰트 변경")
        }
    }
}

@Composable
fun MyPagePreset(onClickMenu: (String) -> Unit) {
    Column() {
        TextButton(onClick = { onClickMenu("contactPreset") }) {
            Text("연락처 프리셋 설정")
        }
        TextButton(onClick = { onClickMenu("messagePreset") }) {
            Text("메시지 프리셋 설정")
        }
    }
}

@Composable
fun MyPageMain(onClickMenu: (String) -> Unit, changeMenuState: (MyPageState) -> Unit) {
    Column() {
        TextButton(onClick = { changeMenuState(MyPageState.Preset) }) {
            Text("프리셋 설정")
        }
        TextButton(onClick = { onClickMenu("greetingPreset") }) {
            Text("인사말 설정")
        }
        TextButton(onClick = { changeMenuState(MyPageState.Style) }) {
            Text("삐삐 꾸미기")
        }
        TextButton(onClick = { changeMenuState(MyPageState.Member) }) {
            Text("회원 설정")
        }
    }
}

