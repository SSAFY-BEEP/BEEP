package com.example.beep.domain

import javax.inject.Inject

class ValidatePasswordCheck @Inject constructor() {

    fun execute(password: String, passwordCheck: String): ValidateResult {
        if (password != passwordCheck) {
            return ValidateResult(
                successful = false,
                errorMessage = "비밀번호가 일치하지 않습니다"
            )
        }

        return ValidateResult(
            successful = true
        )
    }
}