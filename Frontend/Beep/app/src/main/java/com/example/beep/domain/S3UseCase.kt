package com.example.beep.domain

import com.example.beep.data.dto.mypage.S3Request
import com.example.beep.data.repository.S3Repository
import okhttp3.MultipartBody
import javax.inject.Inject

class S3UseCase @Inject constructor(private val s3Repository: S3Repository){
//    val introduceFlow = s3Repository.introduceFlow
    suspend fun getIntroduceUseCase() = s3Repository.getIntroduce()
    suspend fun deleteIntroduceUseCase(request: S3Request) = s3Repository.deleteIntroduce(request)
    suspend fun postIntroduceUseCase(voice: MultipartBody.Part) = s3Repository.postIntroduce(voice)
}