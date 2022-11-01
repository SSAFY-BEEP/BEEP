package com.example.beep.data.repository

import com.example.beep.data.datasource.RetrofitDataSource
import com.example.beep.data.dto.DataModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitRepository @Inject constructor(private val retrofitDataSource: RetrofitDataSource) {
    fun postTest(dataModel: DataModel) =
        flow { retrofitDataSource.retrofitPostTest(dataModel).collect { emit(it) } }
}