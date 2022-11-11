package com.example.beep.ui.savedmessage

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.message.Message24Response
import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.domain.Message24UseCase
import com.example.beep.domain.MessageUseCase
import com.example.beep.ui.message.UiState
import com.example.beep.util.ResultType
import com.example.beep.util.fromJson
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
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
    var currentSavedMessageType by mutableStateOf(SavedMessageType.SEND)

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

    fun deleteMessage(messageId: Long) {
        savedMessageUiState = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            when (messageUseCase.deleteMessage(messageId)) {
                is ResultType.Success -> { Log.d("SavedMessage", "Delete Success") }
                else -> { Log.d("SavedMessage", "Delete Failed") }
            }
            getMessage()
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

    fun changeCurrentSavedMessageType(type: SavedMessageType) {
        Log.d("CurrentSavedMessageType", type.name)
        currentSavedMessageType = type
        getMessage()
    }

    init {
        getMessage()
    }
}