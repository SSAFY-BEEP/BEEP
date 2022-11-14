package com.example.beep.ui.home

import androidx.compose.material.Button
import androidx.compose.runtime.Composable

@Composable
fun BbibbiDoRecord(
    toSendMsg: () -> Unit,
    ) {
    Button(onClick = {
        // 메시지 보내기 묻기 화면으로 가는 버튼
        toSendMsg()
    }) {

    }
}