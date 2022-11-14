package com.example.beep.data.dto.message

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("audioUri") val audioUri: String?,
    @SerializedName("content") val content: String = "",
    @SerializedName("receiverPhoneNumber") val receiverPhoneNumber: String = "",
    @SerializedName("senderPhoneNumber") val senderPhoneNumber: String = "",
    @SerializedName("ownerPhoneNumber") val ownerPhoneNumber: String = "",
    @SerializedName("localDateTime") val localDateTime: String = "",
    @SerializedName("tag") val tag: String?,
    @SerializedName("type") val type: Int
)
