package com.example.beep.data.datasource

import android.util.Log
import com.example.beep.data.dto.mypage.S3Request
import com.example.beep.network.api.S3Api
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class S3DataSource @Inject constructor(private val s3Api: S3Api) {
//    val introduceByFlow: Flow<String> = flow {
//        while (true) {
//            emit(s3Api.getIntroduce())
//            delay(3000)
//            Log.d("FlowTimer", "fetched introduce")
//        }
//    }
    suspend fun getIntroduce(): String = s3Api.getIntroduce()

    fun deleteIntroduce(request: S3Request): Flow<String> = flow {
        emit(s3Api.deleteIntroduce(request))
    }

    fun postIntroduce(voice: MultipartBody.Part): Flow<String> = flow {
        emit(s3Api.postIntroduce(voice))
    }
}