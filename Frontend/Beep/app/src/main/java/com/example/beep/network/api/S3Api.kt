package com.example.beep.network.api

import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.mypage.S3Request
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface S3Api {
    @GET("s3/voice")
    suspend fun getIntroduce(): BaseResponse<String?>

    @PATCH("s3/introduce")
    suspend fun deleteIntroduce(@Body request: S3Request): BaseResponse<String>

    @Multipart
    @POST("s3/introduce")
    suspend fun postIntroduce(@Part voice: MultipartBody.Part): BaseResponse<String>

    @GET("s3/voice/{phone}")
    suspend fun getIntroduceByPhone(@Path("phone") phoneNumber: String): BaseResponse<String>
}