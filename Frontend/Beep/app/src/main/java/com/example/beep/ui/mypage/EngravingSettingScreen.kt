package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.message.MessageSuccessScreen
import com.example.beep.ui.message.ResultState
import com.example.beep.ui.message.UiState

//@Preview
@Composable
fun EngravingSettingScreen(modifier: Modifier = Modifier, model: MyPageViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val currentUiState = model.userDataScreenState) {
            is UiState.Loading -> {
                LoadingScreen()
            }
            is UiState.Success -> {
                EngraveScreen(modifier = Modifier, model = model)
            }
            is UiState.Error -> {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun EngraveScreen(modifier: Modifier = Modifier, model: MyPageViewModel) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BeepForTest()
        TextField(value = model.engraveText, onValueChange = { model.engraveText = it })
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { /*뒤로 가기*/ }) {
                Text(text = "취소")
            }
            Button(onClick = { model.writeEngrave() }) {
                Text(text = "수정")
            }
        }
        Spacer(modifier = modifier.height(100.dp))
    }
}
