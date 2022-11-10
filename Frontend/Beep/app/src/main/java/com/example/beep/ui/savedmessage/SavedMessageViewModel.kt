package com.example.beep.ui.savedmessage

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.message.Message24Response
import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.domain.Message24UseCase
import com.example.beep.domain.MessageUseCase
import com.example.beep.ui.message.UiState
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
    var savedMessageUiState: UiState<Any> by mutableStateOf(UiState.Loading)

    fun changeTag(id: Long, tag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            messageUseCase.changeTag(id, tag).collectLatest {
                if (it.code() == 200) {
                    Log.d("ChangeTag", it.body()!!)
                } else {
                    Log.d("ChangeTag", "Fail!!")
                }
            }
        }
    }

    fun getMessage(type: SavedMessageType) {
        savedMessageUiState = UiState.Loading
        var result: BaseResponse<Any> = BaseResponse("Before Init", "BeforeInit")
        when (type) {
            SavedMessageType.SEND -> {
//                result = messageUseCase.getSend()
            }
            SavedMessageType.RECEIVED -> {
                messageUseCase.getReceive(1)
            }
            SavedMessageType.BLOCKED -> {
                messageUseCase.getReceive(2)
            }
        }
        savedMessageUiState = if (result.status == "OK") {
            UiState.Success(result.data)
        } else {
            UiState.Error
        }
    }

    fun deleteMessage(messageId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            messageUseCase.deleteMessage(messageId).collectLatest {
                if (it.code() == 200) {
                    Log.d("Delete Persistent", it.body()!!)
                } else {
                    Log.d("Delete Persistent", "Fail!!")
                }
            }
        }
    }

    fun blockMessage(messageId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            messageUseCase.blockMessage(messageId).collectLatest {
                if (it.code() == 200) {
                    Log.d("Block Message", it.body()!!)
                } else {
                    Log.d("Block Message", "Fail!!")
                }

            }
        }
    }
}