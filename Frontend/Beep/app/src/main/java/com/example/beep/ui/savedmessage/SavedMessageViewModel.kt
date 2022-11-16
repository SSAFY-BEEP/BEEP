package com.example.beep.ui.savedmessage

import android.media.AudioAttributes
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.domain.MessageUseCase
import com.example.beep.ui.mypage.introduce.UiState
import com.example.beep.util.ResultType
import com.example.beep.util.S3_CONSTANT_URI
import com.example.beep.util.VoicePlayer
import com.example.beep.util.fromJson
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class SavedMessageType {
    SEND, RECEIVED, BLOCKED
}

@HiltViewModel
class SavedMessageViewModel @Inject constructor(
    private val messageUseCase: MessageUseCase,
) :
    ViewModel() {
    val gson = Gson()
    var savedMessageUiState: UiState<List<MessageResponse>> by mutableStateOf(UiState.Loading)
    var currentSavedMessageType by mutableStateOf(SavedMessageType.RECEIVED)
    var savedMessageAudioState: SavedMessageAudioState by mutableStateOf(SavedMessageAudioState())
    var showDeleteDialog by mutableStateOf(false)
    var showModifyDialog by mutableStateOf(false)
    var showBlockDialog by mutableStateOf(false)
    var messageToModify: MessageResponse? by mutableStateOf(null)
    var messageAudioState by mutableStateOf(SavedMessageAudioState())

    fun getMessage() {
        savedMessageUiState = UiState.Loading
        viewModelScope.launch {
            val result = when (currentSavedMessageType) {
                SavedMessageType.SEND -> {
                    messageUseCase.getSend()
                }
                SavedMessageType.RECEIVED -> {
                    messageUseCase.getReceive(1)
                }
                SavedMessageType.BLOCKED -> {
                    messageUseCase.getReceive(2)
                }
            }
            savedMessageUiState = when (result) {
                is ResultType.Success -> {
                    val list = gson.fromJson<List<MessageResponse>>(gson.toJson(result.data.data))

                    UiState.Success(list.map { messageResponse ->
                        messageResponse.copy(
                            localDateTime = messageResponse.localDateTime
                                .replace('-', '/')
                                .replace('T', ' ')
                                .split('.')[0], ownerPhoneNumber = ""
                        )
                    })
                }
                else -> {
                    UiState.Error
                }
            }
        }
    }

    fun deleteMessage() {
        savedMessageUiState = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            if (messageToModify == null) return@launch
            when (messageUseCase.deleteMessage(messageToModify!!.id)) {
                is ResultType.Success -> {
                    Log.d("SavedMessage", "Delete Success")
                    getMessage()
                }
                else -> {
                    Log.d("SavedMessage", "Delete Failed")
                    savedMessageUiState = UiState.Error
                }
            }
        }
    }

    fun blockMessage() {
        if (currentSavedMessageType == SavedMessageType.RECEIVED)
            viewModelScope.launch(Dispatchers.IO) {
                when (messageUseCase.blockMessage(messageToModify!!.id)) {
                    is ResultType.Success -> {
                        Log.d("SavedMessage", "Block Success")
                        getMessage()
                    }
                    else -> {
                        Log.d("SavedMessage", "Block Failed")
                        savedMessageUiState = UiState.Error
                    }
                }
            }
        else
            viewModelScope.launch(Dispatchers.IO) {
                when (messageUseCase.cancelBlock(messageToModify!!.id)) {
                    is ResultType.Success -> {
                        Log.d("SavedMessage", "Cancel Block Success")
                        getMessage()
                    }
                    else -> {
                        Log.d("SavedMessage", "Cancel Block Failed")
                        savedMessageUiState = UiState.Error
                    }
                }
            }
    }

    fun changeTag(tag: String) {
        savedMessageUiState = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (messageUseCase.changeTag(messageToModify!!.id, tag)) {
                is ResultType.Success -> {
                    Log.d("SavedMessage", "Success")
                    getMessage()
                }
                else -> {
                    Log.d("SavedMessage", "Failed")
                    savedMessageUiState = UiState.Error
                }
            }
        }
    }

    fun toggleConfirmDeleteAlert() {
        showDeleteDialog = !showDeleteDialog
    }

    fun toggleModifyTagAlert() {
        showModifyDialog = !showModifyDialog
    }

    fun toggleBlockAlert() {
        showBlockDialog = !showBlockDialog
    }

    fun changeCurrentSavedMessageType(type: SavedMessageType) {
        Log.d("CurrentSavedMessageType", type.name)
        currentSavedMessageType = type
        getMessage()
    }

    fun playSavedMessageAudio(message: MessageResponse) {
        VoicePlayer.nullInstance()
        VoicePlayer.getInstance().apply {
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
            )
            setDataSource(S3_CONSTANT_URI + message.audioUri)
            setOnPreparedListener {
                it.start()
                savedMessageAudioState =
                    savedMessageAudioState.copy(isPlaying = true, message = message)
            }
            prepareAsync()
            setOnCompletionListener {
                if (it.isPlaying)
                    it.stop()
                it.release()
                savedMessageAudioState =
                    savedMessageAudioState.copy(isPlaying = false, message = null)
            }
        }
    }

    fun stopSavedMessageAudio() {
        try {
            VoicePlayer.getInstance().apply {
                if (this.isPlaying)
                    stop()
                release()
                savedMessageAudioState = savedMessageAudioState.copy(isPlaying = false, message = null)
            }
        } catch (e: Exception) {
            Log.e(
                "VoicePlayer",
                "stopSavedMessageAudio",
                e
            )
        }

    }

    init {
        getMessage()
    }
}