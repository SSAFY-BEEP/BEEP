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
import com.example.beep.ui.message.UiState
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
    var savedmessageAudioState: SavedMessageAudioState by mutableStateOf(SavedMessageAudioState())
    var showDialog by mutableStateOf(false)
    var idToDelete by mutableStateOf(-1L)

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
                    UiState.Success(list)
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
            if (idToDelete == -1L) return@launch
            when (messageUseCase.deleteMessage(idToDelete)) {
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

    fun blockMessage(messageId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            when (messageUseCase.blockMessage(messageId)) {
                is ResultType.Success -> {
                    Log.d("SavedMessage", "Block Success")
                }
                else -> {
                    Log.d("SavedMessage", "Block Failed")
                }
            }
            getMessage()
        }
    }

    fun changeTag(id: Long, tag: String) {
        savedMessageUiState = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (messageUseCase.changeTag(id, tag)) {
                is ResultType.Success -> {
                    Log.d("SavedMessage", "Success")
                }
                else -> {
                    Log.d("SavedMessage", "Failed")
                }
            }
            getMessage()
        }
    }

    fun toggleConfirmDeleteAlert() {
        showDialog = !showDialog;
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
                savedmessageAudioState =
                    savedmessageAudioState.copy(isPlaying = true, message = message)
            }
            prepareAsync()
            setOnCompletionListener {
                it.stop()
                it.release()
                savedmessageAudioState =
                    savedmessageAudioState.copy(isPlaying = false, message = null)
            }
        }
    }

    fun stopSavedMessageAudio() {
        VoicePlayer.getInstance().apply {
            stop()
            release()
            savedmessageAudioState = savedmessageAudioState.copy(isPlaying = false, message = null)
        }
    }

    init {
        getMessage()
    }
}