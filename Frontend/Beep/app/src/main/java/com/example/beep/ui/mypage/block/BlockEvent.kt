package com.example.beep.ui.mypage.block

import com.example.beep.data.dto.message.MessageResponse

sealed class BlockEvent {
    data class AskCancelBlock(val message: MessageResponse) : BlockEvent()
    data class AskChangeTag(val message: MessageResponse) : BlockEvent()
    object DismissCancelBlock : BlockEvent()
    object DismissChangeTag : BlockEvent()
    object CancelBlock : BlockEvent()
    data class ChangeTag(val newTag: String) : BlockEvent()
}
