package com.example.beep.data.repository

import android.util.Log
import com.example.beep.data.datasource.AuthDataSource
import com.example.beep.data.datasource.sample.PresetDataSource
import com.example.beep.data.dto.auth.LoginRequest
import com.example.beep.data.dto.auth.SignUpRequest
import com.example.beep.data.dto.auth.TokenResponse
import com.example.beep.data.dto.mypage.PresetResponse
import com.example.beep.util.ResultType
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource
) {

    fun signUp(request: SignUpRequest) = flow {
        emit(ResultType.Loading)
        authDataSource.signUp(request).collect {
            if (it.authority == "ROLE_USER") {
                emit(ResultType.Success(true))
            } else if (it.authority != "ROLE_USER") {
                emit(ResultType.Success(false))
            } else {
                emit(ResultType.Empty)
            }
        }
    }.catch { e ->
        emit(ResultType.Error(e))
    }
    private val TAG = "loginTest"
    fun login(request: LoginRequest) = flow{
        emit(ResultType.Loading)
        authDataSource.login(request).collect {
            Log.d(TAG, "loginUser: $it")
            if (it.token != "") {
                emit(ResultType.Success(true))
            } else if (it.token == "") {
                emit(ResultType.Fail(false))
            } else {
                emit(ResultType.Empty)
            }
        }
    }
}



//interface AuthRepository {
//    suspend fun signUp(phoneNumber: String, password: String, fcmToken: String): AuthResult<Unit>
//    suspend fun login(phoneNumber: String, password: String): AuthResult<Unit>
//}