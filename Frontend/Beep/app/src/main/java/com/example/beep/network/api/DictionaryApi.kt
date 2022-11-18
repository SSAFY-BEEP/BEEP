package com.example.beep.network.api

import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.dictionary.DictionaryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {
    @GET("dictionary/word/{word}")
    suspend fun getDictionaryByWord(@Path("word") word: String?): BaseResponse<List<DictionaryResponse>>
}