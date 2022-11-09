package com.example.beep.data.repository.datasource.sample

import com.example.beep.data.dto.message.MessageRequest
import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.network.api.MessageApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageDataSource @Inject constructor(private val messageApi: MessageApi) {
    fun getReceiveMessage(type: Int): Flow<List<MessageResponse>> = flow {
        emit(messageApi.getReceiveMessage(type))
    }

    fun getSendMessage(): Flow<List<MessageResponse>> = flow {
        emit(messageApi.getSendMessage())
    }

    fun changeTag(messageRequest: MessageRequest): Flow<String> = flow {
        emit(messageApi.changeTag(messageRequest))
    }

    fun deleteMessage(messageId: Long): Flow<String> = flow {
        emit(messageApi.deleteMessage(messageId))
    }
}