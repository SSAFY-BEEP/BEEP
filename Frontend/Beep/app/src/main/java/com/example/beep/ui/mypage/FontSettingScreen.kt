package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FontSettingScreen(modifier: Modifier = Modifier, model: MyPageViewModel) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            TextButton(onClick = {
                model.fontNum = 1
                model.changeFont()
            }) {
                Text(text = "라나픽셀")
            }
            TextButton(onClick = {
                model.fontNum = 2
                model.changeFont()
            }) {
                Text(text = "둥근모")
            }
            TextButton(onClick = {
                model.fontNum = 3
                model.changeFont()
            }) {
                Text(text = "검은 고딕")
            }
            TextButton(onClick = {
                model.fontNum = 4
                model.changeFont()
            }) {
                Text(text = "랩디지털")
            }
        }
        BeepForTest(text = "0123456789ㄱㄴㄷ")
    }
}