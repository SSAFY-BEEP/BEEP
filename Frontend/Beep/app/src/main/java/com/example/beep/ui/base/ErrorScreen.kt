package com.example.beep.ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.beep.R
import com.example.beep.ui.theme.BACKGROUND_WHITE

@Composable
fun ErrorScreen() {
    Box(modifier = Modifier
        .background(color = BACKGROUND_WHITE)
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.beepicon),
            contentDescription = "로딩중"
        )
    }

}