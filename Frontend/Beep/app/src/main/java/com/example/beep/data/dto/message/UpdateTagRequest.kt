package com.example.beep.data.dto.message

import com.google.gson.annotations.SerializedName

data class UpdateTagRequest(
    @SerializedName("id") val id: Long,
    @SerializedName("tag") val tag: String,
)
