package com.example.beep.data.repository

import com.example.beep.data.repository.datasource.S3DataSource
import com.example.beep.data.dto.mypage.S3Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class S3Repository @Inject constructor(private val s3DataSource: S3DataSource) {
    fun getIntroduce(): Flow<String> = flow {
        s3DataSource.getIntroduce().collect { emit(it) }
    }

    fun deleteIntroduce(request: S3Request): Flow<String> = flow {
        s3DataSource.deleteIntroduce(request).collect { emit(it) }
    }

    fun postIntroduce(voice: MultipartBody.Part): Flow<String> = flow {
        s3DataSource.postIntroduce(voice).collect { emit(it) }
    }
}