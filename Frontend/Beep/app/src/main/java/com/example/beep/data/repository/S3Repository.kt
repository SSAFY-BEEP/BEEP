package com.example.beep.data.repository

import com.example.beep.data.datasource.S3DataSource
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.mypage.S3Request
import com.example.beep.util.ResultType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class S3Repository @Inject constructor(private val s3DataSource: S3DataSource) {
//    val introduceFlow: Flow<String> = s3DataSource.introduceByFlow

    suspend fun getIntroduce(): ResultType<String?> = try {
        val result = s3DataSource.getIntroduce()
        if (result.status == "OK")
            ResultType.Success(result.data)
        else
            ResultType.Fail(result.data)
    } catch (e: Exception) {
        ResultType.Error(e)
    }

    suspend fun deleteIntroduce(request: S3Request): ResultType<String> = try {
        val result = s3DataSource.deleteIntroduce(request)
        if (result.status == "OK")
            ResultType.Success(result.data)
        else
            ResultType.Fail(result.data)
    } catch (e: Exception) {
        ResultType.Error(e)
    }


    suspend fun postIntroduce(voice: MultipartBody.Part): ResultType<String> = try {
        val result = s3DataSource.postIntroduce(voice)
        if (result.status == "OK")
            ResultType.Success(result.data)
        else
            ResultType.Fail(result.data)
    } catch (e: Exception) {
        ResultType.Error(e)
    }


    suspend fun getIntroduceByPhone(phoneNumber: String) = try {
        val result = s3DataSource.getIntroduceByPhone(phoneNumber)
        if (result.status == "OK")
            ResultType.Success(result.data)
        else
            ResultType.Fail(result.data)
    } catch (e: Exception) {
        ResultType.Error(e)
    }

}