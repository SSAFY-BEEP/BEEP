package com.example.beep.ui.mypage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.beep.data.sample.contactPresetList

@Composable
fun ContactPresetScreen(modifier: Modifier = Modifier, viewModel: MyPageViewModel) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize(), horizontalAlignment = Alignment.Start
    ) {
        Column(modifier = modifier
            .height(400.dp).fillMaxWidth()
            .verticalScroll(scrollState), horizontalAlignment = Alignment.Start) {
            for (num in 0L..9L) {
                TextButton(modifier = modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
                    Text(text = "$num : ${contactPresetList[num]?.content ?: ""}")
                }
            }
        }
        Column(modifier = modifier.height(200.dp)) {
            BeepForTest()
        }
    }
}