package com.example.beep.data.dto.dictionary

import com.google.gson.annotations.SerializedName

data class DictionaryResponse(
    @SerializedName("word") val word : String,
    @SerializedName("value") val value : String,
)

