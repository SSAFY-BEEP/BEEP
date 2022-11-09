package com.example.beep.domain

import com.example.beep.data.dto.auth.SignUpRequest
import com.example.beep.data.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    fun execute(request: SignUpRequest)
    = authRepository.signUp(request)
}