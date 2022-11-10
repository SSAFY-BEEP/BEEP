package com.example.beep.data.dto

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error") val error: String,
    @SerializedName("code") val code: String,
    @SerializedName("message") val message: String
)
