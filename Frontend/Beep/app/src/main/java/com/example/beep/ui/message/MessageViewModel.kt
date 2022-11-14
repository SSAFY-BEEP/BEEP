package com.example.beep.ui.message

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.message.Message24Response
import com.example.beep.domain.Message24UseCase
import com.example.beep.util.ResultType
import com.example.beep.util.fromJson
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val message24UseCase: Message24UseCase
) :
    ViewModel() {
    var msg24State by mutableStateOf(Message24ScreenState())

    val gson = Gson()


    private fun getMsg24() {
        msg24State = msg24State.copy(resultState = ResultState.Loading)
        viewModelScope.launch {
            val result = when (msg24State.receiveSendState) {
                ReceiveSendState.Receive -> {
                    message24UseCase.getReceive24()
                }
                ReceiveSendState.Send -> {
                    message24UseCase.getSend24()
                }
            }
            var list : List<Message24Response> = emptyList()
            result.collectLatest {
                msg24State = msg24State.copy(resultState = when (it) {
                    is ResultType.Success -> {
                        list = gson.fromJson<List<Message24Response>>(gson.toJson(it.data.data))
                        Log.d("Get Message24 List", list.toString())
                        msg24State = msg24State.copy(msg24List = list)
                        ResultState.Success
                    }
                    else -> {
                        ResultState.Error
                    }
                }, msg24List = list)
            }
        }
    }

    fun saveMsg24() {
        msg24State = msg24State.copy(resultState = ResultState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            if(msg24State.messageToModify == null) return@launch
            message24UseCase.saveMsg(msg24State.messageToModify!!.id).collectLatest {
                when(it){
                    is ResultType.Success -> {
                        Log.d("SaveMessage", "Success!!")
                        getMsg24()
                    }
                    else -> {
                        Log.d("SaveMessage", "Error!!")
                        msg24State = msg24State.copy(resultState = ResultState.Error)
                    }
                }
            }
        }
    }

    fun changeCurrentSavedMessageType(type: ReceiveSendState) {
        Log.d("CurrentSavedMessageType", type.name)
        msg24State = msg24State.copy(receiveSendState = type)
        getMsg24()
    }

    fun toggleConfirmAlert(type: MessagePopupState) {
        msg24State = msg24State.copy(popupState = type)
    }

//    var message24UiState by mutableStateOf(Message24ScreenState())
//
//    fun getMessage24() {
//        viewModelScope.launch(Dispatchers.IO) {
//            if (message24UiState.receiveSendState == ReceiveSendState.Receive) {
//                message24UseCase.getReceive24()
//            } else {
//                message24UseCase.getSend24()
//            }
//        }
//    }
//
//    //message24를 보관 메시지로 저장
//    fun saveMsg24(messageId: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            message24UseCase.saveMsg(messageId).collectLatest {
//                if (it.code() == 200) {
//                    Log.d("SAVE Message24", it.body()!!)
//                } else {
//                    Log.d("SAVE Message24", "Fail!!")
//                }
//            }
//        }
//    }
//
//    fun deleteMsg24(messageId: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            message24UseCase.deleteMsg(messageId).collectLatest {
//                if (it.code() == 200) {
//                    Log.d("Delete Msg24", it.body()!!)
//                } else {
//                    Log.d("Delete Msg24", "Fail!!")
//                }
//            }
//        }
//    }
//
//    fun blockMsg24(messageId: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            message24UseCase.blockMsg(messageId).collectLatest {
//                if (it.code() == 200) {
//                    Log.d("Block Msg24", it.body()!!)
//                } else {
//                    Log.d("Block Msg24", "Fail!!")
//                }
//            }
//        }
//    }
    init {
        getMsg24()
    }
}