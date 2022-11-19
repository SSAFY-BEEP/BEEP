package com.example.beep.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.ui.mypage.introduce.UiState

@RequiresApi(Build.VERSION_CODES.S)
@ExperimentalComposeUiApi
@Composable
fun Bbibbi(
    homeViewModel: HomeViewModel = viewModel(),
    presetViewModel: PresetViewModel = viewModel(),
    keyboardViewModel: KeyboardViewModel = viewModel()
) {


    var contentString = ""


    var sendText by remember { mutableStateOf(false) }

    when (homeViewModel.currentPage) {
        "PutAddress" -> {
            BbibbiPutAddress(
                toPutMsg = {
                    homeViewModel.currentPage = "PutMsg"
                    Log.d(
                        "PushedBtn",
                        "Pushed PutMsg Btn! currentPage : ${homeViewModel.currentPage}"
                    )
                }
            )
        }

        "PutMsg" -> {
            BbibbiPutMsg(
                toPutAddress = { homeViewModel.currentPage = "PutAddress" },
                toAskRecord = { homeViewModel.currentPage = "AskRecord" },
                changeContentString = { changedContentString: String ->
                    contentString = changedContentString
                },
                resetKeyboard = { keyboardViewModel.onAction(KeyboardAction.Clear) }
            )
        }
        "AskRecord" -> {
            BbibbiAskToRecord(
                toPutMsg = { homeViewModel.currentPage = "PutMsg" },
                toSendMsg = { homeViewModel.currentPage = "SendMsg" },
                toRecord = { homeViewModel.currentPage = "DoRecord" },
            )
        }
        "SendMsg" -> {
            BbibbiAskToSend(
                toPutMsg = { homeViewModel.currentPage = "PutMsg" },
                toAskRecord = { homeViewModel.currentPage = "AskRecord" },
                toFirstPage = { homeViewModel.currentPage = "ReceivedMsg" },
            )
        }
        "DoRecord" -> {
            BbibbiDoRecord(
                modifier = Modifier.offset(40.dp, 50.dp),
                toSendMsg = { homeViewModel.currentPage = "SendMsg" },
                toAskRecord = { homeViewModel.currentPage = "AskRecord" }
            )
        }
        "ReceivedMsg" -> {
            var receiveMsg = when (val currentUiState = homeViewModel.receivedMessageUiState) {
                is UiState.Loading -> {
                    "로딩중..."
                }
                is UiState.Error -> {
                    "ERROR"
                }
                is UiState.Success -> {
                    if (currentUiState.data.isEmpty()) "" else currentUiState.data[0].content
                }
            }
            BbibbiShowMessage(
                /* 메시지 내용 String, 발신인 */
                toPutAddress = { homeViewModel.currentPage = "PutAddress" },
                toPutMsg = { homeViewModel.currentPage = "PutMsg" },
                receivedMsg = receiveMsg
            )

        }
        else -> {
            BbibbiPutAddress(
                toPutMsg = { homeViewModel.currentPage = "PutMsg" },
            )
        }
    }

}