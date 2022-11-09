package com.example.beep.domain

import javax.inject.Inject

class ValidateTerms @Inject constructor() {

    fun execute(acceptedTerms: Boolean): ValidateResult {
        if (!acceptedTerms) {
            return ValidateResult(
                successful = false,
                errorMessage = "조건을 수락해주세요"
            )
        }

        return ValidateResult(
            successful = true
        )
    }
}