package com.example.beep.domain

import com.example.beep.data.dto.auth.LoginRequest
import com.example.beep.data.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun execute(request: LoginRequest)
    = authRepository.login(request)
}