package com.example.beep.network.api

import okhttp3.MultipartBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface S3Api {
    @GET("s3/voice/{userId}")
    suspend fun getReview(@Path("userId") userId: Long): String

    @Multipart
    @POST("s3/voice")
    suspend fun postVoiceMessage(@Part("voice") voice: MultipartBody.Part): String

    @Multipart
    @POST("s3/introduce")
    suspend fun postIntroduceVoiceMessage(@Part("voice") voice: MultipartBody.Part): String
}