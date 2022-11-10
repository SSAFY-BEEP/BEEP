package com.example.beep.data.dto.mypage

import com.google.gson.annotations.SerializedName

data class PresetRequest(
    @SerializedName("number") val number: Int,
    @SerializedName("part") val part: Int,
    @SerializedName("content") val content: String,
)
