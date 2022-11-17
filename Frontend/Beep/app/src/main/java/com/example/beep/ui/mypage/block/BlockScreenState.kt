package com.example.beep.ui.mypage.block

import com.example.beep.data.dto.message.MessageResponse

enum class BlockScreenResult {
    Loading, Success, Fail
}

data class BlockScreenState(
    val currentState: BlockScreenResult = BlockScreenResult.Loading,
    val showCancelBlockPopup: Boolean = false,
    val showChangeTagPopup: Boolean = false,
    val blockList: List<MessageResponse> = listOf(),
    val messageToHandle: MessageResponse? = null
)
