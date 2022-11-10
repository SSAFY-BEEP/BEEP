package com.example.beep.data.dto.message

import com.google.gson.annotations.SerializedName
import retrofit2.http.Multipart

data class Message24Request(
    @SerializedName("content") val content : String,
    @SerializedName("receiverNum") val receiverNum : String
)
