package com.example.beep.data.repository

import com.example.beep.data.datasource.UserDataSource
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.mypage.UserInfoResponse
import com.example.beep.util.ResultType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userDataSource: UserDataSource
) {

    fun setSound(request: Int): Flow<ResultType<BaseResponse<String>>> =
        flow {
            emit(ResultType.Loading)
            userDataSource.setSound(request).collect {
                if (it.status == "OK") {
                    emit(ResultType.Success(it))
                } else {
                    emit(ResultType.Fail(it))
                }
            }
        }.catch { e -> emit(ResultType.Error(e)) }

    fun setFont(request: Int): Flow<ResultType<BaseResponse<String>>> =
        flow {
            emit(ResultType.Loading)
            userDataSource.setFont(request).collect {
                if (it.status == "OK") {
                    emit(ResultType.Success(it))
                } else {
                    emit(ResultType.Fail(it))
                }
            }
        }.catch { e -> emit(ResultType.Error(e)) }

    fun setTheme(request: Int): Flow<ResultType<BaseResponse<String>>> =
        flow {
            emit(ResultType.Loading)
            userDataSource.setTheme(request).collect {
                if (it.status == "OK") {
                    emit(ResultType.Success(it))
                } else {
                    emit(ResultType.Fail(it))
                }
            }
        }.catch { e -> emit(ResultType.Error(e)) }

    fun setEngrave(request: String): Flow<ResultType<BaseResponse<String>>> =
        flow {
            emit(ResultType.Loading)
            userDataSource.setEngrave(request).collect {
                if (it.status == "OK") {
                    emit(ResultType.Success(it))
                } else {
                    emit(ResultType.Fail(it))
                }
            }
        }.catch { e -> emit(ResultType.Error(e)) }

    fun getUserInfo() : Flow<ResultType<BaseResponse<UserInfoResponse>>> =
        flow {
            emit(ResultType.Loading)
            userDataSource.getUserInfo().collect {
                if (it.status == "OK") {
                    emit(ResultType.Success(it))
                } else {
                    emit(ResultType.Fail(it))
                }
            }
        }.catch { e -> emit(ResultType.Error(e)) }
}

