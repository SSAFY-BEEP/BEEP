package com.example.beep.ui.message

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.message.Message24Response
import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.domain.Message24UseCase
import com.example.beep.domain.MessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val messageUseCase: MessageUseCase, private val message24UseCase: Message24UseCase) :
    ViewModel() {

    private val type = 1

    //보관 메시지 리스트
    val receiveMessages: Flow<List<MessageResponse>> = messageUseCase.getReceive(type)
    val sendMessages: Flow<List<MessageResponse>> = messageUseCase.getSend()
    //24시간 후에 사라지는 일반 메시지 리스트
    val receiveMsg24: Flow<List<Message24Response>> = message24UseCase.getReceive24()
    val sendMsg24: Flow<List<Message24Response>> = message24UseCase.getSend24()

    fun changeTag(id: Long, tag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            messageUseCase.changeTag(id, tag).collectLatest {
                Log.d("changeTag", it)
            }
        }

    }

    fun deleteMessage(messageId : Long) {
        viewModelScope.launch(Dispatchers.IO) {
            messageUseCase.deleteMessage(messageId).collectLatest {
                Log.d("Delete Persistent", it)
            }
        }
    }

    fun blockMessage(messageId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            messageUseCase.blockMessage(messageId).collectLatest {
                Log.d("Block Message", it)
            }
        }
    }

    //message24를 보관 메시지로 저장
    fun saveMsg24(messageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            message24UseCase.saveMsg(messageId).collectLatest {
                Log.d("API REQUEST", "SAVE MSG24")
            }
        }
    }

    fun deleteMsg24(messageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            message24UseCase.deleteMsg(messageId).collectLatest {
                Log.d("API REQUEST", "DELETE MSG24")
            }
        }
    }

    fun blockMsg24(messageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            message24UseCase.blockMsg(messageId).collectLatest {
                Log.d("API REQUEST", "BLOCK MSG24")
            }
        }
    }
}