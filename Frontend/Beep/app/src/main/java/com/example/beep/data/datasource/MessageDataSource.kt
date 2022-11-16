package com.example.beep.data.datasource

import android.util.Log
import com.example.beep.data.dto.BaseResponse
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
    suspend fun getReceiveMessage(type: Int): BaseResponse<List<MessageResponse>> =
        messageApi.getReceiveMessage(type)

    suspend fun getSendMessage(): BaseResponse<List<MessageResponse>> = messageApi.getSendMessage()

    suspend fun changeTag(id: Long, tag: String): BaseResponse<String> =
        messageApi.changeTag(MessageRequest(id, tag))

    suspend fun deleteMessage(messageId: Long): BaseResponse<String> =
        messageApi.deleteMessage(messageId)

    suspend fun blockMessage(messageId: Long): BaseResponse<String> =
        messageApi.blockMessage(messageId)

    suspend fun cancelBlock(messageId: Long): BaseResponse<String> =
        messageApi.cancelBlock(messageId)

    suspend fun cancelSave(messageId: Long): BaseResponse<String> =
        messageApi.cancelSave(messageId)

}