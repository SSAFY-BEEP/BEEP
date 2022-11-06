package com.example.beep.data.dto.mypage

import com.google.gson.annotations.SerializedName

data class PresetResponse(
    @SerializedName("pid") val id: Long,
    @SerializedName("uid") val userId: Long,
    @SerializedName("number") val number: Int,
    @SerializedName("part") val part: Int,
    @SerializedName("content") val content: String,
)
