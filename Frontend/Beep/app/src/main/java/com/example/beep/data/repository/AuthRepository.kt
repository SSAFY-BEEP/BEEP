package com.example.beep.data.repository

import com.example.beep.data.dto.auth.AuthResult

interface AuthRepository {
    suspend fun signUp(phoneNumber: String, password: String, fcmToken: String): AuthResult<Unit>
    suspend fun login(phoneNumber: String, password: String): AuthResult<Unit>
}