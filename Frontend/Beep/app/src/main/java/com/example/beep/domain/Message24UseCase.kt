package com.example.beep.domain

import com.example.beep.data.dto.message.Message24Request
import com.example.beep.data.repository.Message24Repository
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Message24UseCase @Inject constructor(private val message24Repository: Message24Repository){
    fun getReceive24() = message24Repository.getReceiveMsg24()
    fun getSend24() = message24Repository.getSendMsg24()
    fun get24(messageId : String) = message24Repository.getMsg24(messageId)
    fun sendMsg(file: MultipartBody.Part?, message: Message24Request) = message24Repository.sendMsg(file, message)
    fun saveMsg(messageId: String) = message24Repository.saveMsg(messageId)
    fun deleteMsg(messageId: String) = message24Repository.deleteMsg(messageId)
    fun blockMsg(messageId: String) = message24Repository.blockMsg(messageId)
}