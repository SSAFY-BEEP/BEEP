package com.example.beep.data.datasource

import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.message.Message24Request
import com.example.beep.data.dto.message.Message24Response
import com.example.beep.network.api.Message24Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Message24DataSource @Inject constructor(private val message24Api: Message24Api){
    fun getReceiveMsg24() : Flow<BaseResponse<List<Message24Response>>> = flow {
        emit(message24Api.getReceiveMsg24())
    }

    fun getSendMsg24() : Flow<BaseResponse<List<Message24Response>>> = flow {
        emit(message24Api.getSendMsg24())
    }

    fun getMsg24(id : String) : Flow<BaseResponse<Message24Response>> = flow {
        emit(message24Api.getMsg24(id))
    }

    fun sendMsg(file : MultipartBody.Part?, message:Message24Request): Flow<BaseResponse<String>>
    = flow {
        emit(message24Api.sendMessage(file, message))
    }

    fun saveMsg(messageId: String) : Flow<BaseResponse<String>> = flow {
        emit(message24Api.saveMessage(messageId))
    }

    fun deleteMsg(messageId: String) : Flow<BaseResponse<String>> = flow {
        emit(message24Api.deleteMessage(messageId))
    }

    fun blockMsg(messageId: String) : Flow<BaseResponse<String>> = flow {
        emit(message24Api.blockMessage(messageId))
    }
}