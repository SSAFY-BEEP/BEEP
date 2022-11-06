package com.example.beep.data.dto.sample

import com.google.gson.annotations.SerializedName

data class RetrofitTestResponse(
    @SerializedName("name") val name: String,
    @SerializedName("job") val job: String
)
