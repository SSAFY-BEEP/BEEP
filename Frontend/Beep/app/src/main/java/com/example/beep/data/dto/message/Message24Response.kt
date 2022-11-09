package com.example.beep.data.dto.message

import com.google.gson.annotations.SerializedName

data class Message24Response (
    @SerializedName("id") val id: String,
    @SerializedName("content") val content: String,
    @SerializedName("time") val time: String,
    @SerializedName("audioUri") val audioUri: String?,
    @SerializedName("senderNum") val senderPhoneNumber: String,
    @SerializedName("receiverNum") val receiverPhoneNumber: String,
    @SerializedName("ownerNum") val ownerPhoneNumber: String,
    @SerializedName("type") val type: Int
)