package com.example.beep.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BbibbiShowMessage(
    /* 메시지1개내용 */
    /* 메시지1개발신인 */
    toPutAddress: () -> Unit,
    toPutMsg: () -> Unit

) {
    Button(
        // 연락처 입력 페이지로
        onClick = {
            /* cancel 버튼 */
            toPutAddress()
            Log.d("BUTTON", "CLICKED")
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
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue.copy(0.2F)),
        shape = RoundedCornerShape(5.dp, 5.dp, 5.dp, 30.dp)
    ) {
    }
    Button(
        // 바로 메시지 입력 페이지로
        onClick = {
            /* go버튼 */
            toPutMsg(
                /* 수신한 메시지의 발신자 연락처 */
            )
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
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue.copy(0.2F)),
        shape = RoundedCornerShape(65.dp, 20.dp, 50.dp, 0.dp)
    ) {

    }
    Text(
        text = "메시지내용0345",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(top = 45.dp),
        fontSize = 19.sp,
        fontFamily = galmurinineFont
    )
}