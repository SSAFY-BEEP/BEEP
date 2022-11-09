package com.example.beep.domain

import android.util.Patterns
import javax.inject.Inject

class ValidatePhoneNumber @Inject constructor() {

    fun execute(phoneNumber: String): ValidateResult {
        if (phoneNumber.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "아이디를 입력해주세요"
            )
        }
        if (phoneNumber.length != 11) {
            return ValidateResult(
                successful = false,
                errorMessage = "번호 11자리를 확인해주세요"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}