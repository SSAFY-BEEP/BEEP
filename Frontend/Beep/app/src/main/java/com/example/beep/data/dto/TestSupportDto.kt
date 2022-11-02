package com.example.beep.data.dto

import com.google.gson.annotations.SerializedName

data class TestSupportDto(
    @SerializedName("url") val url: String,
    @SerializedName("text") val text: String,
)
