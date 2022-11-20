package com.example.beep.data.dto.message

import com.google.gson.annotations.SerializedName

data class BlockResponse(
    @SerializedName("id") val id: String,
    @SerializedName("messageId") val messageId: String,
    @SerializedName("userId") val userId: String,
    @SerializedName("targetId") val targetId: String,
)
