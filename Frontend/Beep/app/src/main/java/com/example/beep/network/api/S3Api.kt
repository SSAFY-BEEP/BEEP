package com.example.beep.network.api

import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.mypage.S3Request
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface S3Api {
    @GET("s3/voice")
    suspend fun getIntroduce(): BaseResponse<String>

    @PATCH("s3/introduce")
    suspend fun deleteIntroduce(request: S3Request): BaseResponse<String>

    @Multipart
    @POST("s3/introduce")
    suspend fun postIntroduce(@Part voice: MultipartBody.Part): BaseResponse<String>
}