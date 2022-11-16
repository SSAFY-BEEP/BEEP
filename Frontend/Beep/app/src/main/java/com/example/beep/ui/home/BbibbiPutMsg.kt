package com.example.beep.ui.home

import android.os.Handler
import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.ui.mypage.introduce.UiState

@ExperimentalComposeUiApi
@Composable
fun BbibbiPutMsg(
    toPutAddress: () -> Unit,
    toAskRecord: () -> Unit,
    changeContentString: (String) -> Unit,
    resetKeyboard: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(),
    ) {
    Log.d("PageMove", "Moved to PutMessage")

    val keyboardViewModel = viewModel<KeyboardViewModel>()

    var defaultNameString by remember { mutableStateOf("") }


    /* cancel 버튼 */
    ResetButton(
        modifier = Modifier
    ) {
        homeViewModel.resetMessageToSend()
        // 입력값 리셋 필요
        keyboardViewModel.onAction(KeyboardAction.Clear)
        // 다시 연락처 입력 페이지로
        toPutAddress()
    }

    Button(
        onClick = {
            /* go버튼 */
            if (defaultNameString == "메시지를 입력해주세요") {
                defaultNameString = "=(๑º ﾛ º๑)"
                // 내용을 입력해주세요
            } else if (defaultNameString == "=(๑º ﾛ º๑)") {
                // 내용을 입력해주세요
            } else if (defaultNameString.length < 12) {
                homeViewModel.setMessageContent(keyboardViewModel.state.number1)
                // 음성을 녹음하시겠습니까
                changeContentString(defaultNameString)
                toAskRecord()
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
        defaultNameString = defaultNameString
    )
}


@Composable
fun ViewMyText(
    changeDefaultNameString: (String) -> Unit,
    defaultNameString: String,

) {
    val presetViewModel = viewModel<PresetViewModel>()
    when (val currentUiState = presetViewModel.contactPreset) {
        is UiState.Loading -> {
            changeDefaultNameString("로딩중...")
        }
        is UiState.Success -> {
            if (currentUiState.data.isEmpty()) {
            } else {
//                receiveMsg = currentUiState.data[0].content
                Log.d("데이터0::::::", currentUiState.data.toString())
            }
        }
        is UiState.Error -> {
            changeDefaultNameString("ERROR")
        }
    }

    val viewModel = viewModel<KeyboardViewModel>()
    val state = viewModel.state
    var show = if (state.number1.isNotEmpty()) {
        state.number1
    } else if (defaultNameString == "=(๑º ﾛ º๑)") {
        "=(๑º ﾛ º๑)"
    } else {
        "메시지를 입력해주세요"
    }


    changeDefaultNameString(show)


    if (defaultNameString == "=(๑º ﾛ º๑)") {
        Handler().postDelayed({
            show = "메시지를 입력해주세요"
            changeDefaultNameString(show)
        }, 1000)


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




