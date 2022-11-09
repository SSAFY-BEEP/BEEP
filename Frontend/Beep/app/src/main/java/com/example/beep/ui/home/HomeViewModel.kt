package com.example.beep.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.message.Message24Response
import com.example.beep.domain.Message24UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val message24UseCase: Message24UseCase) :
ViewModel() {

    //24시간 후에 사라지는 일반 메시지 리스트
    val receiveMsg24: Flow<List<Message24Response>> = message24UseCase.getReceive24()
    val sendMsg24: Flow<List<Message24Response>> = message24UseCase.getSend24()

    fun getOne24(messageId : String) : Message24Response? {
        var result : Message24Response? = null
        viewModelScope.launch(Dispatchers.IO) {
            message24UseCase.get24(messageId).collectLatest {
                Log.d("getOne", it.id)
                result = it
            }
        }
        return result
    }

    fun sendMsg(file: MultipartBody.Part?, content: String, receiverNum: String) {
        Log.d("POST REQUEST", "content : $content, receiverNum : $receiverNum")
        viewModelScope.launch(Dispatchers.IO) {
            message24UseCase.sendMsg(file, content, receiverNum).collectLatest {
                Log.d("POST RESULT", it.toString())
            }
        }
    }

    fun saveMessage(messageId: String) {
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