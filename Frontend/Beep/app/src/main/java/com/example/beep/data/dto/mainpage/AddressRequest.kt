package com.example.beep.data.dto.mainpage

import com.google.gson.annotations.SerializedName

data class AddressRequest(
    @SerializedName("phone") val phone: String,
    @SerializedName("name") val name: String,
)

