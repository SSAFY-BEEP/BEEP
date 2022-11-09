package com.example.beep.data.datasource

import android.util.Log
import com.example.beep.data.dto.message.MessageRequest
import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.network.api.MessageApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageDataSource @Inject constructor(private val messageApi: MessageApi) {
    fun getReceiveMessage(type: Int): Flow<Response<List<MessageResponse>>> = flow {
        emit(messageApi.getReceiveMessage(type))
    }

    fun getSendMessage(): Flow<Response<List<MessageResponse>>> = flow {
        emit(messageApi.getSendMessage())
    }

    fun changeTag(id: Long, tag: String): Flow<Response<String>> = flow {
        val response = messageApi.changeTag(MessageRequest(id, tag))
        Log.d("Response", response.code().toString())
        emit(response)
    }

    fun deleteMessage(messageId: Long): Flow<Response<String>> = flow {
        emit(messageApi.deleteMessage(messageId))
    }

    fun blockMessage(messageId: Long) : Flow<Response<String>> = flow {
        emit(messageApi.blockMessage(messageId))
    }
}