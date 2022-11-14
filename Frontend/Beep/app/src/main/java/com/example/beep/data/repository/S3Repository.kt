package com.example.beep.data.repository

import com.example.beep.data.datasource.S3DataSource
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.mypage.S3Request
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class S3Repository @Inject constructor(private val s3DataSource: S3DataSource) {
//    val introduceFlow: Flow<String> = s3DataSource.introduceByFlow

    suspend fun getIntroduce(): BaseResponse<String> = s3DataSource.getIntroduce()

    suspend fun deleteIntroduce(request: S3Request): BaseResponse<String> =
        s3DataSource.deleteIntroduce(request)

    suspend fun postIntroduce(voice: MultipartBody.Part): BaseResponse<String> =
        s3DataSource.postIntroduce(voice)
}