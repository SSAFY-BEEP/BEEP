package com.example.beep.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BbibbiAskToSend(
    toPutMsg: () -> Unit,
    toAskRecord: () -> Unit,
    toFirstPage: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(),
) {

    var go by remember { mutableStateOf(true) }

    var r = 0
    var l = 0
    if (go) {
        r = 140
        l = 0
    } else {
        r = 0
        l = 60
    }

    Button(
        // 메시지 입력 페이지로(메시지 내용 살아있음)
        onClick = {
            /* cancel 버튼 */
                  toPutMsg()
        },
        modifier = Modifier
            .width(69.dp)
            .offset(60.dp, 133.dp)
            .height(45.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta.copy(0.2F)),
        shape = RoundedCornerShape(5.dp, 5.dp, 5.dp, 30.dp)
    ) {
    }

    Button(
        // <-
        onClick = {
            go = !go
        },
        modifier = Modifier
            .width(60.dp)
            .offset(130.dp, 133.dp)
            .height(45.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta.copy(0.2F)),
        shape = RoundedCornerShape(5.dp)
    ) {

    }

    Button(
        // ->
        onClick = {
            go = !go
        },
        modifier = Modifier
            .width(68.dp)
            .offset(190.dp, 133.dp)
            .height(45.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta.copy(0.2F)),
        shape = RoundedCornerShape(5.dp, 0.dp, 40.dp, 5.dp)
    ) {

    }

    Button(
        // 녹음페이지로
        onClick = {
            /* go버튼 */
            if (go) {
                // 메시지보내기 action
            } else {
                //  첫 페이지로
                toFirstPage()
            }
        },
        modifier = Modifier
            .width(83.dp)
            .offset(252.dp, 110.dp)
            .height(67.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta.copy(0.2F)),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(65.dp, 20.dp, 50.dp, 0.dp)
    ) {

    }

    Text(
        text = "메시지를 보내시겠습니까?",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(top = 35.dp),
        fontSize = 17.sp,
        fontFamily = galmurinineFont
    )

    Button(
        // 메시지 보내기
        onClick = {
            /* yes버튼 */
        },
        modifier = Modifier
            .width(60.dp)
            .offset(120.dp, 63.dp)
            .height(30.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta.copy(0.0F)),
//        shape = RoundedCornerShape(65.dp, 20.dp, 50.dp, 0.dp),
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(
            text = "YES",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(0.dp),
            fontSize = 15.sp,
            fontFamily = galmurinineFont
        )
    }
    Button(
        // 첫 페이지로
        onClick = {
            /* no버튼 */
            toFirstPage()
        },
        modifier = Modifier
            .width(60.dp)
            .offset(220.dp, 63.dp)
            .height(30.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta.copy(0.0F)),
//        shape = RoundedCornerShape(65.dp, 20.dp, 50.dp, 0.dp),
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(
            text = "NO ",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(0.dp),
            fontSize = 15.sp,
            fontFamily = galmurinineFont
        )
    }

    Text(
        text = ">",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(l.dp, 66.dp, r.dp, 0.dp),
        fontSize = 15.sp,
        fontFamily = galmurinineFont
    )

}