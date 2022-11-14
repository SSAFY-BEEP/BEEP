package com.example.beep.ui.savedmessage

import com.example.beep.data.dto.message.MessageResponse

data class SavedMessageAudioState(
    val isPlaying: Boolean = false,
    val message: MessageResponse? = null
)
