package com.example.beep.data.repository

import com.example.beep.data.datasource.S3DataSource
import com.example.beep.data.dto.mypage.S3Request
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class S3Repository @Inject constructor(private val s3DataSource: S3DataSource) {
//    val introduceFlow: Flow<String> = s3DataSource.introduceByFlow

    suspend fun getIntroduce() : String = s3DataSource.getIntroduce()

    fun deleteIntroduce(request: S3Request): Flow<String> = flow {
        s3DataSource.deleteIntroduce(request).collect { emit(it) }
    }

    fun postIntroduce(voice: MultipartBody.Part): Flow<String> = flow {
        s3DataSource.postIntroduce(voice).collect { emit(it) }
    }
}