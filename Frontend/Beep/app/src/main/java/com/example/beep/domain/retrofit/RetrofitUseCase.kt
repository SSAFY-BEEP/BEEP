package com.example.beep.domain.retrofit

import com.example.beep.data.dto.sample.DataModel
import com.example.beep.data.repository.RetrofitRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitUseCase @Inject constructor(private val retrofitRepository: RetrofitRepository) {
    fun postData(dataModel: DataModel) = retrofitRepository.postTest(dataModel)
    fun getData() = retrofitRepository.getTest()
}