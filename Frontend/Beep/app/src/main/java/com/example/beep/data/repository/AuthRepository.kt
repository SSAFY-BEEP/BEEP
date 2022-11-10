package com.example.beep.data.repository

import com.example.beep.data.dto.auth.*
import com.example.beep.data.datasource.AuthDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource
) {

    fun signUp(request: SignUpRequest): Flow<SignUpResponse> =
        flow { authDataSource.signUp(request).collect { emit(it) } }

    fun login(request: LoginRequest): Flow<LoginResponse> =
        flow { authDataSource.login(request).collect { emit(it)} }

    fun newPassword(request: NewPasswordRequest): Flow<String> =
        flow { authDataSource.newPassword(request).collect { emit(it)} }

    fun withdrawal(): Flow<String> =
        flow { authDataSource.withdrawal().collect { emit(it)} }

}


//interface AuthRepository {
//    suspend fun signUp(phoneNumber: String, password: String, fcmToken: String): AuthResult<Unit>
//    suspend fun login(phoneNumber: String, password: String): AuthResult<Unit>
//}