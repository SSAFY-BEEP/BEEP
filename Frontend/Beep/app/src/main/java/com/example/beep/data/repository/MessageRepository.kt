package com.example.beep.data.repository

import com.example.beep.data.repository.datasource.MessageDataSource
import com.example.beep.data.dto.message.MessageResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class MessageRepository @Inject constructor(private val messageDataSource: MessageDataSource) {
    fun getReceiveMessage(type: Int): Flow<Response<List<MessageResponse>>> =
        flow { messageDataSource.getReceiveMessage(type).collect { emit(it)} }

    fun getSendMessage(): Flow<Response<List<MessageResponse>>> =
        flow { messageDataSource.getSendMessage().collect { emit(it)} }

    fun changeTag(id: Long, tag: String): Flow<Response<String>> =
        flow { messageDataSource.changeTag(id, tag).collect { emit(it)} }

    fun deleteMessage(messageId : Long): Flow<Response<String>> =
        flow { messageDataSource.deleteMessage(messageId).collect {emit(it)} }

    fun blockMessage(messageId: Long): Flow<Response<String>> = flow {
        messageDataSource.blockMessage(messageId).collect { emit(it) }
    }
}