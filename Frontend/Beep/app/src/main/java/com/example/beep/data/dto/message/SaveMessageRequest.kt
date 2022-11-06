package com.example.beep.data.dto.message

import com.google.gson.annotations.SerializedName

data class SaveMessageRequest(
    @SerializedName("audioUri") val audioUri: String,
    @SerializedName("content") val content: String,
    @SerializedName("receiverPhoneNumber") val receiverPhoneNumber: String,
    @SerializedName("senderPhoneNumber") val senderPhoneNumber: String,
    @SerializedName("localDateTime") val localDateTime: String,
    @SerializedName("tag") val tag: String,
)
