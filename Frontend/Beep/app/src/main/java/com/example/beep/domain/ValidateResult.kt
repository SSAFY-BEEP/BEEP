package com.example.beep.domain

data class ValidateResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
