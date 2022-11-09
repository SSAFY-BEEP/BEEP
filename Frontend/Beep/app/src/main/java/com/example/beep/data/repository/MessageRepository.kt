package com.example.beep.data.repository

import com.example.beep.data.datasource.MessageDataSource
import com.example.beep.data.dto.message.MessageResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MessageRepository @Inject constructor(private val messageDataSource: MessageDataSource) {
    fun getReceiveMessage(type: Int): Flow<List<MessageResponse>> =
        flow { messageDataSource.getReceiveMessage(type).collect { emit(it)} }

    fun getSendMessage(): Flow<List<MessageResponse>> =
        flow { messageDataSource.getSendMessage().collect { emit(it)} }

    fun changeTag(id: Long, tag: String): Flow<String> =
        flow { messageDataSource.changeTag(id, tag).collect { emit(it)} }

    fun deleteMessage(messageId : Long): Flow<String> =
        flow { messageDataSource.deleteMessage(messageId).collect {emit(it)} }

    fun blockMessage(messageId: Long) = flow {
        messageDataSource.blockMessage(messageId).collect { emit(it) }
    }
}