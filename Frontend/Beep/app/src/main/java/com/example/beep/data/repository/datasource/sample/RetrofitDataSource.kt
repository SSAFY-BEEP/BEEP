package com.example.beep.data.repository.datasource.sample

import com.example.beep.network.api.RetrofitApi
import com.example.beep.data.dto.sample.DataModel
import com.example.beep.data.dto.sample.RetrofitTestGetResponse
import com.example.beep.data.dto.sample.RetrofitTestResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitDataSource @Inject constructor(private val retrofitApi: RetrofitApi) {
    fun retrofitPostTest(dataModel: DataModel): Flow<RetrofitTestResponse> = flow {
        emit(retrofitApi.postData(dataModel))
    }

    fun testGetData(): Flow<RetrofitTestGetResponse> = flow {
        emit(retrofitApi.getData())
    }
}