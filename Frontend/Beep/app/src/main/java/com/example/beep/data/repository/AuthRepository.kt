package com.example.beep.data.repository

import com.example.beep.data.dto.auth.*
import com.example.beep.data.datasource.AuthDataSource
import com.example.beep.data.dto.BaseResponse
import com.example.beep.util.ResultType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource
) {

    fun signUp(request: SignUpRequest): Flow<ResultType<BaseResponse<SignUpResponse>>> = flow {
        emit(ResultType.Loading)
            authDataSource.signUp(request).collect {
                if (it.status == "OK") {
                    emit(ResultType.Success(it))
                } else {
                    emit(ResultType.Fail(it))
                }
            }
        }. catch { e ->
            emit(ResultType.Error(e))
        }

    fun login(request: LoginRequest): Flow<ResultType<BaseResponse<LoginResponse>>> = flow {
        emit(ResultType.Loading)
        authDataSource.login(request).collect {
            if (it.status == "OK") {
                emit(ResultType.Success(it))
            } else {
                emit(ResultType.Fail(it))
            }
        }
    }. catch { e ->
        emit(ResultType.Error(e))
    }

    fun newPassword(request: NewPasswordRequest): Flow<BaseResponse<String>> =
        flow { authDataSource.newPassword(request).collect { emit(it)} }

    fun withdrawal(): Flow<BaseResponse<String>> =
        flow { authDataSource.withdrawal().collect { emit(it)} }

}


//interface AuthRepository {
//    suspend fun signUp(phoneNumber: String, password: String, fcmToken: String): AuthResult<Unit>
//    suspend fun login(phoneNumber: String, password: String): AuthResult<Unit>
//}