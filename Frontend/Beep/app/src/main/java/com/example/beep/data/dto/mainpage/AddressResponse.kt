package com.example.beep.data.dto.mainpage

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("install") val name: Int,
    @SerializedName("name") val userName: String,
    @SerializedName("phone") val phone: String,
)

