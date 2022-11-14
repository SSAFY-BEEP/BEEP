package com.example.beep.ui.message

import com.example.beep.data.dto.message.MessageResponse

enum class MessagePopupState {
    NORMAL, DELETE, BLOCK
}

enum class ResultState {
    Loading, Success, Fail, Error
}

enum class ReceiveSendState {
    Receive, Send
}

data class Message24ScreenState(
    val resultState: ResultState = ResultState.Loading,
    val receiveSendState: ReceiveSendState = ReceiveSendState.Receive,
    val msg24List: List<MessageResponse> = listOf(),
    val messageToModify: MessageResponse? = null,
    val popupState: MessagePopupState = MessagePopupState.NORMAL
)