package com.example.beep.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BbibbiDoRecord(
    toSendMsg: () -> Unit,
    toAskRecord: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val currentState = homeViewModel.recordScreenState
    Column(
        modifier = modifier
//            .background(Color.Cyan)
            .width(320.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DoRecordScreen(currentState = currentState)
        Spacer(modifier = modifier.height(35.dp))
        Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.Bottom) {
            CancelBtn(currentState = currentState)
            LeftBtn(currentState = currentState)
            RightBtn(currentState = currentState)
            ConfirmBtn(currentState = currentState, toSendMsg = toSendMsg)
        }
    }
}

@Composable
fun ConfirmBtn(
    modifier: Modifier = Modifier,
    currentState: RecordScreenState,
    toSendMsg: () -> Unit
) {
    Button(
        modifier = modifier.height(67.dp),
        shape = RoundedCornerShape(65.dp, 20.dp, 50.dp, 0.dp),
        onClick = {
            // 메시지 보내기 묻기 화면으로 가는 버튼
            toSendMsg()
        }) {

    }
    when (currentState) {
        RecordScreenState.Before -> {}
        RecordScreenState.Recording -> {}
        RecordScreenState.Finished -> {}
        RecordScreenState.Playing -> {}
    }


}

@Composable
fun RightBtn(modifier: Modifier = Modifier, currentState: RecordScreenState) {
    Button(onClick = { /*TODO*/ }, modifier = modifier.background(Color.Red)) {

    }
    when (currentState) {
        RecordScreenState.Before -> {}
        RecordScreenState.Recording -> {}
        RecordScreenState.Finished -> {}
        RecordScreenState.Playing -> {}
    }
}

@Composable
fun LeftBtn(modifier: Modifier = Modifier, currentState: RecordScreenState) {
    Button(onClick = { /*TODO*/ }, modifier = modifier.background(Color.Blue)) {

    }
    when (currentState) {
        RecordScreenState.Before -> {}
        RecordScreenState.Recording -> {}
        RecordScreenState.Finished -> {}
        RecordScreenState.Playing -> {}
    }
}

@Composable
fun CancelBtn(modifier: Modifier = Modifier, currentState: RecordScreenState) {
    Button(onClick = { /*TODO*/ }, modifier = modifier.background(Color.Green)) {
    }
    when (currentState) {
        RecordScreenState.Before -> {}
        RecordScreenState.Recording -> {}
        RecordScreenState.Finished -> {}
        RecordScreenState.Playing -> {}
    }
}

@Composable
fun DoRecordScreen(modifier: Modifier = Modifier, currentState: RecordScreenState) {
    Text(text = "BBIBBI Screen", modifier = modifier.background(Color.Black), fontSize = 19.sp)
    when (currentState) {
        RecordScreenState.Before -> {}
        RecordScreenState.Recording -> {}
        RecordScreenState.Finished -> {}
        RecordScreenState.Playing -> {}
    }
}
