package com.example.beep.data.datasource

import com.example.beep.data.api.RetrofitApi
import com.example.beep.data.dto.DataModel
import com.example.beep.data.dto.RetrofitTestGetResponse
import com.example.beep.data.dto.RetrofitTestResponse
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