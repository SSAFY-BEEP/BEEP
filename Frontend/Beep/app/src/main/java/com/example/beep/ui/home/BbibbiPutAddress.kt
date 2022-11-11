package com.example.beep.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@ExperimentalComposeUiApi
@Composable
fun BbibbiPutAddress(
    toPutMsg: () -> Unit
) {
    val viewModel = viewModel<KeyboardViewModel>()


    /* cancel 버튼 */
    ResetButton(
        modifier = Modifier
    ) {
        // 연락처 입력해놓은거 리셋시키기
        viewModel.onAction(KeyboardAction.Clear)
    }

    Button(
        // 메시지 입력 페이지로
        onClick = {
            /* go버튼 */
            toPutMsg(
                /* 입력한 연락처 */
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
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red.copy(0.2F)),
        shape = RoundedCornerShape(65.dp, 20.dp, 50.dp, 0.dp)
    ) {

    }

    ViewAskOrInputNum()
}


@Composable
fun ViewAskOrInputNum() {
    val viewModel = viewModel<KeyboardViewModel>()
    val state = viewModel.state
    var show = if (state.number1.isNotEmpty()) {
        state.number1
    } else {
        "연락처를 입력해주세요"
    }
    Text(
        text = show,
        modifier = Modifier

            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(top = 48.dp),
        fontSize = 19.sp,
        fontFamily = galmurinineFont
    )
}
