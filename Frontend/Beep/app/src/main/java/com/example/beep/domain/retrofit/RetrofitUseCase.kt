package com.example.beep.domain.retrofit

import com.example.beep.data.dto.DataModel
import com.example.beep.data.repository.RetrofitRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitUseCase @Inject constructor(private val retrofitRepository: RetrofitRepository) {
    fun execute(dataModel: DataModel) = retrofitRepository.postTest(dataModel)
}