package com.example.beep.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.beep.R

@Composable
fun BeepForTest(modifier: Modifier = Modifier, text: String = "") {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.bbibbi_white),
            contentDescription = "삐삐 이미지",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .width(320.dp),
            contentScale = ContentScale.FillWidth
        )
        Text(modifier = modifier.align(Alignment.Center).offset(y = (-30).dp), text = text)
    }
}