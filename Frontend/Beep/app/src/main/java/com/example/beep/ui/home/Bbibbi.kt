package com.example.beep.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.message.Message24Response
import com.example.beep.util.collectAsStateLifecycleAware

@ExperimentalComposeUiApi
@Composable
fun Bbibbi(
    homeViewModel: HomeViewModel = viewModel(),
    presetViewModel: PresetViewModel = viewModel()
) {
    var sendText by remember { mutableStateOf(false) }
    val receiveMsg = homeViewModel.receiveMsg24.collectAsStateLifecycleAware(
        initial = emptyList<BaseResponse<Message24Response>>()
    );
//    Log.d("Message24 Receive!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", receiveMsg.value.toString())

    var currentPage by remember { mutableStateOf("ReceivedMsg") }


    if (currentPage == "PutAddress") {
        BbibbiPutAddress(
            toPutMsg = {currentPage = "PutMsg"}
        )
        Log.d("currentPage 글씨",currentPage)
    } else if (currentPage == "PutMsg") {
        BbibbiPutMsg(
            toPutAddress = {currentPage = "PutAddress"},
            toAskRecord = {currentPage = "AskRecord"}

            )
    } else if (currentPage == "AskRecord") {
        BbibbiAskToRecord (
            toPutMsg = {currentPage = "PutMsg"},
            toSendMsg = {currentPage = "SendMsg"}
        )
    } else if (currentPage == "SendMsg") {
        BbibbiAskToSend()
    } else if (receiveMsg.value.toString().length > 10) {
        BbibbiShowMessage(
        /* 메시지 내용 String, 발신인 */
        toPutAddress = {currentPage = "PutAddress"},
            toPutMsg = {currentPage = "PutMsg"}
        )
    } else {
        BbibbiPutAddress(
            toPutMsg = {currentPage = "PutMsg"},
        )
    }
}


//        //임시로 메시지 보내기 넣음
//        onClick = {
//            /* cancel 버튼 */
//            homeViewModel.sendMsg(null, "5012", "01012345678")
//        },