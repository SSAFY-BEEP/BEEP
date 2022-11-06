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

@Preview
@Composable
fun EngravingSettingScreen(modifier: Modifier = Modifier) {
    var engravingText by remember { mutableStateOf("KTH") }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BeepForTest()
        TextField(value = engravingText, onValueChange = { engravingText = it })
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "취소")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "수정")
            }
        }
        Spacer(modifier = modifier.height(100.dp))
    }
}