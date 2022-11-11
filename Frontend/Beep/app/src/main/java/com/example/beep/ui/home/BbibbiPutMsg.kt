package com.example.beep.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@ExperimentalComposeUiApi
@Composable
fun BbibbiPutMsg(
    toPutAddress: () -> Unit,

    ) {

    val viewModel = viewModel<KeyboardViewModel>()

    var defaultNameString by remember { mutableStateOf("") }


    /* cancel 버튼 */
    ResetButton(
        modifier = Modifier
    ) {
        // 입력값 리셋 필요
        viewModel.onAction(KeyboardAction.Clear)
        // 다시 연락처 입력 페이지로
        toPutAddress()
    }

    Button(
        onClick = {
            /* go버튼 */
                  if (defaultNameString.isEmpty()) {
                      // 내용을 입력해주세요
                  } else if (defaultNameString.length < 12) {
                      // 음성을 녹음하시겠습니까
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
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green.copy(0.3F)),
        shape = RoundedCornerShape(65.dp, 20.dp, 50.dp, 0.dp)
    ) {

    }
    ViewMyText(
        changeDefaultNameString = { defaultName: String -> defaultNameString = defaultName },
        )
}


@Composable
fun ViewMyText(
    changeDefaultNameString: (String) -> Unit,
    ) {
    val viewModel = viewModel<KeyboardViewModel>()
    val state = viewModel.state
    var show = if (state.number1.isNotEmpty()) {
        state.number1
    } else {
        "메시지를 입력해주세요"
    }

    changeDefaultNameString(show)

    Text(
        text = show,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(top = 45.dp),
        fontSize = 19.sp,
        fontFamily = galmurinineFont
    )
}



@ExperimentalComposeUiApi
@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
//    toPutAddress: () -> Unit,
    ) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .offset(60.dp, 133.dp)
            .clip(RoundedCornerShape(5.dp, 5.dp, 5.dp, 30.dp))
            .width(69.dp)
            .height(45.dp)
            .clickable {
                onClick()
            }
            .background(color = Color.Green.copy(0.3F))
    ) {
    }
}




