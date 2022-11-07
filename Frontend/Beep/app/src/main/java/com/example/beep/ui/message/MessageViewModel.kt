package com.example.beep.ui.message

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.message.MessageRequest
import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.domain.MessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val messageUseCase: MessageUseCase) :
    ViewModel() {

    private val type = 1

    val receiveMessages: Flow<List<MessageResponse>> = messageUseCase.getReceive(type)
    val sendMessages: Flow<List<MessageResponse>> = messageUseCase.getSend()

    private fun changeTag(messageRequest: MessageRequest) {
        val result = messageUseCase.changeTag(messageRequest)
        Log.d("changeTag","result: $result")
    }

    fun deleteMessage(messageId : Long) {
        viewModelScope.launch(Dispatchers.IO) {
            messageUseCase.deleteMessage(messageId).collectLatest {
                if (it == "Success"){
                    Log.d("delete", "Success")
                }
            }
        }
    }
}