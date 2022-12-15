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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.util.SoundEffectPlayer
import com.example.beep.util.SoundEffectType

@Composable
fun BbibbiShowMessage(
    /* 메시지1개내용 */
    /* 메시지1개발신인 */
    toPutAddress: () -> Unit,
    toPutMsg: () -> Unit,
    receivedMsg: String,
    receiveMsgTime : String,
//    homeViewModel: HomeViewModel = viewModel(),
) {
    Log.d("PageMove", "Moved to ShowMessage, receivedMsg = $receivedMsg")
    val keyboardViewModel = viewModel<KeyboardViewModel>()

    Button(
        // 연락처 입력 페이지로
        onClick = {
            SoundEffectPlayer.playSoundEffect(SoundEffectType.BeepBtn)
            /* cancel 버튼 */
            toPutAddress()
            keyboardViewModel.onAction(KeyboardAction.Clear)
            Log.d("BUTTON", "CLICKED")
        },
        modifier = Modifier
            .offset(24.dp, 135.dp)
            .width(69.dp)
            .height(42.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue.copy(0.0F)),
        shape = RoundedCornerShape(5.dp, 5.dp, 5.dp, 30.dp)
    ) {
    }
    Button(
        // 바로 메시지 입력 페이지로
        onClick = {
            SoundEffectPlayer.playSoundEffect(SoundEffectType.BeepBtn)
            /* go버튼 */
            toPutMsg()
            keyboardViewModel.onAction(KeyboardAction.Clear)
        },
        modifier = Modifier
            .width(83.dp)
            .offset(214.dp, 112.dp)
            .height(64.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue.copy(0.0F)),
        shape = RoundedCornerShape(65.dp, 20.dp, 50.dp, 0.dp)
    ) {

    }
    Text(
        text = if (receiveMsgTime.isNotEmpty()) { receiveMsgTime.substring(11, 16) } else {""},
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.Start)
            .padding(55.dp, 38.dp, 0.dp, 0.dp),
        fontSize = 10.sp,
    )
    Text(
        text = receivedMsg,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(top = 48.dp),
        fontSize = 19.sp,
    )
}