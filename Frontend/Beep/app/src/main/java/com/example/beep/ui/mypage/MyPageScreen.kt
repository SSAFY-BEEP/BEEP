package com.example.beep.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beep.di.MainApplication
import com.example.beep.ui.theme.BACKGROUND_WHITE
import com.example.beep.ui.theme.GRAY100


enum class MyPageState {
    Main, Preset, Style, Member
}

@Composable
fun MyPageScreen(onClickMenu: (String) -> Unit) {
    var currentStage by remember { mutableStateOf(MyPageState.Main) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BACKGROUND_WHITE),
        contentAlignment = Alignment.Center,
    ) {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)) {

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
}


@Composable
fun MyPageMember(onClickMenu: (String) -> Unit) {
    customText1( {onClickMenu("passwordChange")},"비밀번호 변경")

    TextButton(onClick =  { MainApplication.sharedPreferencesUtil.deleteToken()}, modifier = Modifier.fillMaxWidth().drawBehind {
        val borderSize = 2.dp.toPx()
        val  y = size.height - borderSize /2
        drawLine(
            color = GRAY100,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = borderSize
        )
    }.padding(2.dp)) {
        Text("로그아웃", fontSize = 16.sp, modifier = Modifier.padding(7.dp))
    }

    TextButton(onClick =  { /* 회원 탈퇴 메서드 */ }, modifier = Modifier.fillMaxWidth().drawBehind {
        val borderSize = 2.dp.toPx()
        val  y = size.height - borderSize /2
        drawLine(
            color = GRAY100,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = borderSize
        )
    }.padding(2.dp)) {
        Text("회원탈퇴", fontSize = 16.sp, modifier = Modifier.padding(7.dp))
    }

}

@Composable
fun MyPageStyle(onClickMenu: (String) -> Unit) {
    customText1( {onClickMenu("colorSetting")},"테마색 설정")
    customText1( {onClickMenu("engravingSetting")},"각인 설정")
    customText1( {onClickMenu("fontSetting")},"폰트 설정")
}

@Composable
fun MyPagePreset(onClickMenu: (String) -> Unit) {
    customText1( {onClickMenu("contactPreset")},"연락처 단축키 설정")
    customText1( {onClickMenu("messagePreset")},"메시지 단축키 설정")
}

@Composable
fun MyPageMain(onClickMenu: (String) -> Unit, changeMenuState: (MyPageState) -> Unit) {
    customText2( {changeMenuState(MyPageState.Preset)},"단축키 설정")
    customText1( {onClickMenu("greetingPreset")},"인사말 설정")
    customText2( {changeMenuState(MyPageState.Style)},"테마 설정")
    customText2( {changeMenuState(MyPageState.Member)},"회원 설정")
}

@Composable
fun customText1(onClickMenu: () -> Unit, text: String){
    TextButton(onClick = onClickMenu, modifier = Modifier.fillMaxWidth().drawBehind {
        val borderSize = 2.dp.toPx()
        val  y = size.height - borderSize /2
        drawLine(
            color = GRAY100,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = borderSize
        )
    }.padding(2.dp)) {
        Text(text, fontSize = 16.sp, modifier = Modifier.padding(7.dp))
    }
}

@Composable
fun customText2(changeMenuState: () -> Unit, text: String){
    TextButton(onClick =  changeMenuState, modifier = Modifier.fillMaxWidth().drawBehind {
        val borderSize = 2.dp.toPx()
        val  y = size.height - borderSize /2
        drawLine(
            color = GRAY100,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = borderSize
        )
    }.padding(2.dp)) {
        Text(text, fontSize = 16.sp, modifier = Modifier.padding(7.dp))
    }
}