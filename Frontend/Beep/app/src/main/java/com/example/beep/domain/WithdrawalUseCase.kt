package com.example.beep.domain

import com.example.beep.data.dto.auth.NewPasswordRequest
import com.example.beep.data.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WithdrawalUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun execute()
    = authRepository.withdrawal()
}