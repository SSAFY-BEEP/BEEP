package com.example.beep.domain

import javax.inject.Inject

class ValidateLoginPhoneNumber @Inject constructor() {

    fun execute(LoginPhoneNumber: String): ValidateResult {
        if(LoginPhoneNumber.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "아이디를 입력해주세요"
            )
        }
        if(LoginPhoneNumber.length != 11) {
            return ValidateResult(
                successful = false,
                errorMessage = "아이디를 11자리 입력해주세요"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}