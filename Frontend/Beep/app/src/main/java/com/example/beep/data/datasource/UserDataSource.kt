package com.example.beep.data.datasource

import com.example.beep.data.dto.BaseResponse
import com.example.beep.network.api.UserApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataSource @Inject constructor(
    private val userApi : UserApi
) {
    fun setSound(
        request: Integer
    ): Flow<BaseResponse<Any>> = flow {
        emit(userApi.setSound(request))
    }

    fun setFont(
        request: Integer
    ): Flow<BaseResponse<Any>> = flow {
        emit(userApi.setFont(request))
    }

    fun setTheme(
        request: Integer
    ): Flow<BaseResponse<Any>> = flow {
        emit(userApi.setTheme(request))
    }

    fun setEngrave(
        request: String
    ): Flow<BaseResponse<Any>> = flow {
        emit(userApi.setEngrave(request))
    }
}