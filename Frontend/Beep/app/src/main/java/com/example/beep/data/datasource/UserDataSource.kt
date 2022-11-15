package com.example.beep.data.datasource

import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.mypage.UserInfoResponse
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
        request: Int
    ): Flow<BaseResponse<String>> = flow {
        emit(userApi.setSound(request))
    }

    fun setFont(
        request: Int
    ): Flow<BaseResponse<String>> = flow {
        emit(userApi.setFont(request))
    }

    fun setTheme(
        request: Int
    ): Flow<BaseResponse<String>> = flow {
        emit(userApi.setTheme(request))
    }

    fun setEngrave(
        request: String
    ): Flow<BaseResponse<String>> = flow {
        emit(userApi.setEngrave(request))
    }

    fun getUserInfo() : Flow<BaseResponse<UserInfoResponse>> = flow {
        emit(userApi.getUserInfo())
    }
}