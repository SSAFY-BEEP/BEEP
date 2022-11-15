package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.beep.ui.theme.*

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
                Text(text = "갈무리", fontFamily = galmurinineFont)
            }

            TextButton(onClick = {
                model.fontNum = 2
                model.changeFont()
            }) {
                Text(text = "둥근모", fontFamily = dunggeunmmoFont)
            }

            TextButton(onClick = {
                model.fontNum = 3
                model.changeFont()
            }) {
                Text(text = "랩디지털", fontFamily = labDigitalFont)
            }
            TextButton(onClick = {
                model.fontNum = 4
                model.changeFont()
            }) {
                Text(text = "라나픽셀", fontFamily = lanaPixelFont)
            }
        }
        BeepImage(modifier = Modifier,"폰트 테스트",model.fontNum)
    }
}