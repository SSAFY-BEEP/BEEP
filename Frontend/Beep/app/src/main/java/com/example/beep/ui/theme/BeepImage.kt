package com.example.beep.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beep.R

@Composable
fun BeepImage(
    modifier: Modifier = Modifier, text: String = "",
    selectBeepImage: Int= 3, selectFontStyle: Int=1, engrave: String? ="") {



    Box(modifier = modifier) {
        Image(
            painter =
            when(selectBeepImage) {
                1 -> painterResource(R.drawable.bbibbi_white)
                2 -> painterResource(R.drawable.bbibbi_black)
                3 -> painterResource(R.drawable.bbibbi_blue)
                else -> painterResource(R.drawable.bbibbi_blue)
            },
            contentDescription = "삐삐 이미지",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .width(320.dp),
            contentScale = ContentScale.FillWidth
        )
        // 각인 자리 어떻게 넣을지 offset을 맞춰주면 될듯
        if (engrave != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text="$engrave",
                    modifier = Modifier
                        .offset(0.dp, 100.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 15.sp,
                    color = Color(android.graphics.Color.parseColor("#505050")),
                    fontFamily = lanaPixelFont
                )
            }
        }


        Text(modifier = modifier
            .align(Alignment.Center)
            .offset(y = (-30).dp), text = text,
            fontFamily = when(selectFontStyle){
                1 -> galmurinineFont
                2 -> dunggeunmmoFont
                3 -> labDigitalFont
                4 -> lanaPixelFont
                else -> galmurinineFont
            })
    }
}