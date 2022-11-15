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
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.message.UiState
import com.example.beep.util.collectAsStateLifecycleAware

@ExperimentalComposeUiApi
@Composable
fun Bbibbi(
    homeViewModel: HomeViewModel = viewModel(),
    presetViewModel: PresetViewModel = viewModel()
) {
    var receiveMsg = ""
//    var senderPhoneNumber = ""
//    var receiverPhoneNumber = ""
    var currentPage by remember { mutableStateOf("ReceivedMsg") }

    when (val currentUiState = homeViewModel.receivedMessageUiState) {
        is UiState.Loading -> {
            receiveMsg = "로딩중..."
        }
        is UiState.Success -> {
            if (currentUiState.data.isEmpty()) {
                receiveMsg = "데이터가 없습니다"
                currentPage = "PutAddress"
            } else {
                receiveMsg = currentUiState.data[0].content

            }
//            val senderPhoneNumber = currentUiState.data[0].senderPhoneNumber
//            val receiverPhoneNumber = currentUiState.data[0].receiverPhoneNumber
//            Log.d("데이터", currentUiState.data[0].senderPhoneNumber)
        }
        is UiState.Error -> {
            receiveMsg = "ERROR"
        }
    }

    var contentString = ""



    var sendText by remember { mutableStateOf(false) }

//    Log.d("Message24 Receive!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!", receiveMsg.value.toString())





    if (currentPage == "PutAddress") {
        BbibbiPutAddress(
            toPutMsg = {currentPage = "PutMsg"}
        )
    } else if (currentPage == "PutMsg") {
        BbibbiPutMsg(
            toPutAddress = {currentPage = "PutAddress"},
            toAskRecord = {currentPage = "AskRecord"},
            changeContentString = { changedContentString: String -> contentString = changedContentString },

            )
    } else if (currentPage == "AskRecord") {
        BbibbiAskToRecord (
            toPutMsg = {currentPage = "PutMsg"},
            toSendMsg = {currentPage = "SendMsg"},
            toRecord = {currentPage = "DoRecord"},
            )
    } else if (currentPage == "SendMsg") {
        BbibbiAskToSend(
            toPutMsg = {currentPage = "PutMsg"},
            toAskRecord = {currentPage = "AskRecord"},
            toFirstPage = {currentPage = "ReceivedMsg"},
        )
    } else if (currentPage == "DoRecord") {
        BbibbiDoRecord(
            toSendMsg = {currentPage = "DoRecord"},
            )
    } else if (receiveMsg.isNotEmpty()) {
        BbibbiShowMessage(
        /* 메시지 내용 String, 발신인 */
        toPutAddress = {currentPage = "PutAddress"},
            toPutMsg = {currentPage = "PutMsg"},
            receivedMsg = receiveMsg
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