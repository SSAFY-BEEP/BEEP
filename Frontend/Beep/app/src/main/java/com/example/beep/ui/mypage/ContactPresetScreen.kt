package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.beep.data.sample.contactPresetList
import com.example.beep.data.sample.messagePresetList

@Preview
@Composable
fun ContactPresetScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(modifier = modifier
            .height(400.dp)
            .verticalScroll(scrollState)) {
            for (num in 0L..9L) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "$num : ${contactPresetList[num]?.content ?: ""}")
                }
            }
        }
        Column(modifier = modifier.height(200.dp)) {
            BeepForTest()
        }
    }
}