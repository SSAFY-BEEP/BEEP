package com.example.beep.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.message.Message24Request
import com.example.beep.data.dto.message.Message24Response
import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.domain.Message24UseCase
import com.example.beep.ui.message.RecordState
import com.example.beep.ui.message.ResultState
import com.example.beep.ui.message.UiState
import com.example.beep.ui.savedmessage.SavedMessageType
import com.example.beep.util.ResultType
import com.example.beep.util.VoicePlayer
import com.example.beep.util.VoiceRecorder
import com.example.beep.util.fromJson
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject


enum class ReceivedMessageType {
    SEND, RECEIVED, BLOCKED
}

enum class RecordMessageState {
    Before, Recording, Finished, Playing, Error, Loading
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val message24UseCase: Message24UseCase
) :
    ViewModel() {

    val gson = Gson()

    //24시간 후에 사라지는 일반 메시지 리스트
    val receiveMsg24: Flow<ResultType<BaseResponse<List<Message24Response>>>> =
        message24UseCase.getReceive24()
    val sendMsg24: Flow<ResultType<BaseResponse<List<Message24Response>>>> =
        message24UseCase.getSend24()
    var receivedMessageUiState: UiState<List<Message24Response>> by mutableStateOf(UiState.Loading)
    var currentReceivedMessageType by mutableStateOf(SavedMessageType.RECEIVED)
    var messageToSend: Message24Request by mutableStateOf(Message24Request())
    var recordScreenState by mutableStateOf(RecordMessageState.Before)
    var timer by mutableStateOf(0)


    fun getOne24() {
        receivedMessageUiState = UiState.Loading
        viewModelScope.launch() {
            message24UseCase.getReceive24().collectLatest {
                if (it is ResultType.Success) {
                    val list = gson.fromJson<List<Message24Response>>(gson.toJson(it.data.data))
                    receivedMessageUiState = UiState.Success(list)
                } else {
                    UiState.Error
                }
            }
        }
    }

    fun sendMsg(file: MultipartBody.Part? = null) {
        Log.d("Send REQUEST", "$file $messageToSend")
        viewModelScope.launch(Dispatchers.IO) {
            message24UseCase.sendMsg(file, messageToSend).collectLatest {
                if (it is ResultType.Success) {
                    Log.d("Send Message", it.data.toString())
                } else {
                    Log.d("Send Message", "Fail!!")
                }
            }
        }
    }

    fun saveMessage(messageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            message24UseCase.saveMsg(messageId).collectLatest {
                if (it is ResultType.Success) {
                    Log.d("Save Message24", it.data.toString())
                } else {
                    Log.d("Save Message24", "Fail!!")
                }
            }
        }
    }

    fun deleteMsg24(messageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            message24UseCase.deleteMsg(messageId).collectLatest {
                if (it is ResultType.Success) {
                    Log.d("Delete Message24", it.data.toString())
                } else {
                    Log.d("Delete Message24", "Fail!!")
                }
            }
        }
    }

    fun blockMsg24(messageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            message24UseCase.blockMsg(messageId).collectLatest {
                if (it is ResultType.Success) {
                    Log.d("Block Message24", it.data.toString())
                } else {
                    Log.d("Block Message24", "Fail!!")
                }
            }
        }
    }

    fun resetMessageToSend() {
        messageToSend = Message24Request()
    }

    fun setMessageContent(content: String) {
        messageToSend = messageToSend.copy(content = content)
    }

    fun setMessageReceiverNum(receiverNum: String) {
        messageToSend = messageToSend.copy(receiverNum = receiverNum)
    }

    init {
        getOne24()
    }
}