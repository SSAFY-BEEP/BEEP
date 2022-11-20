package com.example.beep.ui.home

import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.util.SoundEffectPlayer
import com.example.beep.util.SoundEffectType

@ExperimentalComposeUiApi
@Composable
fun BbibbiPutAddress(
    toPutMsg: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(),
) {
    val viewModel = viewModel<KeyboardViewModel>()
    Log.d("PageMove", "Moved to PutAddress")

    /* cancel 버튼 */
    ResetButton(
        modifier = Modifier
    ) {
        SoundEffectPlayer.playSoundEffect(SoundEffectType.BeepBtn)
        homeViewModel.resetMessageToSend()
        // 연락처 입력해놓은거 리셋시키기
        viewModel.onAction(KeyboardAction.Clear)
    }

    Button(
        // 메시지 입력 페이지로
        onClick = {
            SoundEffectPlayer.playSoundEffect(SoundEffectType.BeepBtn)
            if (homeViewModel.checkAddress(viewModel.state.number1)) {
                homeViewModel.setMessageReceiverNum(viewModel.state.number1)
                viewModel.onAction(KeyboardAction.Clear)
                /* go버튼 */
                toPutMsg(
                    /* 입력한 연락처 */
                )
            } else {
                Log.d("PutAddress", "Not Valid : ${viewModel.state.number1}")
                homeViewModel.showToast("010으로 시작하는 11자리 전화번호를 입력해주세요")
            }
        },
        modifier = Modifier
            .width(83.dp)
            .offset(212.dp, 110.dp)
            .height(67.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red.copy(0.0F)),
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
