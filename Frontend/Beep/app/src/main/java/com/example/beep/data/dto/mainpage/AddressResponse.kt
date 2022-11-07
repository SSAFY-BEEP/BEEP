package com.example.beep.data.dto.mainpage

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("install") val install: Int,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
)

