package com.example.beep.data.repository

import com.example.beep.data.datasource.UserDataSource
import com.example.beep.data.dto.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userDataSource: UserDataSource
) {

    fun setSound(request: Integer): Flow<BaseResponse<Any>> =
        flow { userDataSource.setSound(request).collect(){emit(it)} }

    fun setFont(request: Integer): Flow<BaseResponse<Any>> =
            flow { userDataSource.setFont(request).collect(){emit(it)} }

    fun setTheme(request: Integer): Flow<BaseResponse<Any>> =
            flow { userDataSource.setTheme(request).collect(){emit(it)} }

    fun setEngrave(request: String): Flow<BaseResponse<Any>> =
        flow { userDataSource.setEngrave(request).collect(){emit(it)} }
}

