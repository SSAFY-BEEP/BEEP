package com.example.beep.network.api

import com.example.beep.data.dto.message.Message24Request
import com.example.beep.data.dto.message.Message24Response
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface Message24Api {
    @GET("message24/receive")
    suspend fun getReceiveMsg24() : List<Message24Response>
    @GET("message24/send")
    suspend fun getSendMsg24() : List<Message24Response>
    @GET("message24/{id}")
    suspend fun getMsg24(id : String) : Message24Response

    @Multipart
    @POST("message24/sendFile")
    suspend fun sendMessage(@Part file : MultipartBody.Part?, @Part("message24") message24Request: Message24Request)
    @POST("message24/save/{messageId}")
    suspend fun saveMessage(@Path("messageId") messageId: String)
    @DELETE("message24")
    suspend fun deleteMessage(@Query("messageId") messageId : String)
    @PATCH("message24/block/{messageId}")
    suspend fun blockMessage(@Path("messageId") messageId: String)
}