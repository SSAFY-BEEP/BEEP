package com.example.beep.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalComposeUiApi
@Composable
fun KeyboardButton(
//    symbol: String,
    modifier: Modifier = Modifier,
//    description: String,
    paint: Painter,
    textStyle: TextStyle = TextStyle(),
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .width(110.dp)
            .height(55.dp)
//            .background(Color.White)
//            .border(
//                width = 1.dp,
//                color = Color.Black,
//                shape = RoundedCornerShape(18.dp)
//            )
//            .combinedClickable (
//                onClick = {},
//                onLongClick = {}
//            )
            .clickable {
                onClick()
            }
            .then(modifier)
    ) {
        Image(
            painter = paint,
            contentDescription = "description",
            modifier = Modifier
                .fillMaxSize()
        )
        Text(
            text = "",
            style = textStyle,
            fontSize = 20.sp,
            color = Color.Black
        )
    }
}