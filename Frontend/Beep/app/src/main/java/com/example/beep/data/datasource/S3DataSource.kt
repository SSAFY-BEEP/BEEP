package com.example.beep.data.datasource

import android.util.Log
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.mypage.S3Request
import com.example.beep.network.api.S3Api
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response
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
    suspend fun getIntroduce(): BaseResponse<String?> = s3Api.getIntroduce()

    suspend fun deleteIntroduce(request: S3Request): BaseResponse<String?> = s3Api.deleteIntroduce(request)

    suspend fun postIntroduce(voice: MultipartBody.Part): BaseResponse<String> =
        s3Api.postIntroduce(voice)

    suspend fun getIntroduceByPhone(phoneNumber: String): BaseResponse<String> = s3Api.getIntroduceByPhone(phoneNumber)
}