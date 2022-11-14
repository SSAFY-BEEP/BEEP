package com.example.beep.ui.message

import com.example.beep.data.dto.message.Message24Response

enum class MessagePopupState {
    NORMAL, DELETE, BLOCK, SAVE
}

enum class ResultState {
    Loading, Success, Fail, Error
}

enum class ReceiveSendState {
    Receive, Send
}

data class MessageAudioState(
    val isPlaying: Boolean = false,
    val message: Message24Response? = null
)

data class Message24ScreenState(
    val resultState: ResultState = ResultState.Loading,
    val receiveSendState: ReceiveSendState = ReceiveSendState.Receive,
    val msg24List: List<Message24Response> = listOf(),
    val messageToModify: Message24Response? = null,
    val popupState: MessagePopupState = MessagePopupState.NORMAL,
    val messageAudioState: MessageAudioState = MessageAudioState()
)