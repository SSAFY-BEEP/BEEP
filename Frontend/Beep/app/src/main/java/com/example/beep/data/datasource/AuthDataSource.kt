package com.example.beep.data.datasource

import com.example.beep.data.dto.auth.*
import com.example.beep.network.api.AuthApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataSource @Inject constructor(
    private val authApi: AuthApi
) {
    fun signUp(
        request: SignUpRequest
    ): Flow<SignUpResponse> = flow {
        emit(authApi.signUp(request))
    }

    fun login(
        request: LoginRequest
    ): Flow<LoginResponse> = flow {
        emit(authApi.login(request))
    }

    fun newPassword(
        request: NewPasswordRequest
    ): Flow<String> = flow {
        emit(authApi.newPassword(request))
    }

    fun withdrawal(
    ): Flow<String> = flow {
        emit(authApi.withdrawal())
    }
}
