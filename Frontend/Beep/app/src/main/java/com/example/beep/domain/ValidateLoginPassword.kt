package com.example.beep.domain

import javax.inject.Inject

class ValidateLoginPassword @Inject constructor() {

    fun execute(LoginPassword: String): ValidateResult {
        if(LoginPassword.length < 6) {
            return ValidateResult(
                successful = false,
                errorMessage = "비밀번호 6자리이상 입력해주세요"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}