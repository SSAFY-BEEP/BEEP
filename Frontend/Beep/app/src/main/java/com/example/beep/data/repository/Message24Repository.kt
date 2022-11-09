package com.example.beep.data.repository

import com.example.beep.data.datasource.Message24DataSource
import com.example.beep.data.dto.message.Message24Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import javax.inject.Inject

class Message24Repository @Inject constructor(private val message24DataSource: Message24DataSource) {
    fun getReceiveMsg24() : Flow<List<Message24Response>> =
        flow { message24DataSource.getReceiveMsg24().collect { emit(it) }}

    fun getSendMsg24() : Flow<List<Message24Response>> =
        flow { message24DataSource.getSendMsg24().collect { emit(it) }}

    fun getMsg24(id : String) : Flow<Message24Response> =
        flow { message24DataSource.getMsg24(id).collect { emit(it) }}

    fun sendMsg(file : MultipartBody.Part?, content : String, receiverNum : String) =
        flow { message24DataSource.sendMsg(file, content, receiverNum).collect { emit(it) }}

    fun saveMsg(messageId: String) =
        flow { message24DataSource.saveMsg(messageId).collect { emit(it) }}

    fun deleteMsg(messageId: String) =
        flow { message24DataSource.deleteMsg(messageId).collect { emit(it)} }

    fun blockMsg(messageId: String) =
        flow { message24DataSource.blockMsg(messageId).collect {emit(it)} }
}