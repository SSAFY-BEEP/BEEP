package com.example.beep.data.dto

import com.google.gson.annotations.SerializedName

data class TestDataDto(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("avatar") val avatar: String
) {
}