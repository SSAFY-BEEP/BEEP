package com.example.beep.domain

import javax.inject.Inject

class ValidatePassword @Inject constructor() {

    fun execute(password: String): ValidateResult {
        if (password.length < 5) {
            return ValidateResult(
                successful = false,
                errorMessage = "비밀번호를 6자리 이상 입력해주세요"
            )
        }
        val containsLettersAndDigits = password.any {it.isDigit() } &&
                password.any { it.isLetter() }

        if (!containsLettersAndDigits) {
            return ValidateResult(
                successful = false,
                errorMessage = "하나 이상의 번호/문자를 포함해주세요"
            )
        }
        return ValidateResult(
            successful = true
        )
    }
}