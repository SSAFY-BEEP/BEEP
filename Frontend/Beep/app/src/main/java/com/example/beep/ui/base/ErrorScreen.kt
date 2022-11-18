package com.example.beep.ui.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beep.R
import com.example.beep.ui.theme.BACKGROUND_WHITE
import com.example.beep.ui.theme.PINK500
import com.example.beep.ui.theme.galmurinineFont

@Composable
fun ErrorScreen() {
    Box(modifier = Modifier
        .background(color = BACKGROUND_WHITE)
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "ERROR",
                fontFamily = galmurinineFont,
                color = PINK500,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(0.dp, 40.dp)
            )
        }
    }

}