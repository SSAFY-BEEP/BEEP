package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.beep.di.MainApplication
import com.example.beep.ui.home.getKeyboard
import com.example.beep.ui.theme.BeepImage

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ColorSettingScreen( model: MyPageViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 0.dp, 0.dp, 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(modifier = Modifier) {
            BeepImage(modifier = Modifier,"",model.themeNum)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    model.themeNum = 1
                }) {
                    Text(text = "하양")
                }
                Button(onClick = {
                    model.themeNum = 2
                }) {
                    Text(text = "블랙핑크")
                }
                Button(onClick = {
                    model.themeNum = 3
                }) {
                    Text(text = "파랑")
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {  }) {
                Text(text = "뒤로가기")
            }
            Button(onClick = {
                model.changeTheme()
                MainApplication.sharedPreferencesUtil.saveTheme(model.themeNum)
            }) {
                Text(text = "설정")
            }
        }
    }
}