package com.example.beep.data.dto

import com.google.gson.annotations.SerializedName

data class BaseResponse<out T>(
    @SerializedName("status") val status: String,
    @SerializedName("data") val data: T
)