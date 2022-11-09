package com.example.beep.network.api

import com.example.beep.data.dto.mypage.PresetRequest
import com.example.beep.data.dto.mypage.PresetResponse
import retrofit2.Response
import retrofit2.http.*

interface PresetApi {
    @GET("preset/find/{uid}")
    suspend fun getUserPreset(@Path("uid") uid: Long): Response<List<PresetResponse>>

    @GET("preset/find")
    suspend fun getUserPresetByToken(): Response<List<PresetResponse>>

    @POST("preset/save")
    suspend fun updatePreset(@Body preset: PresetRequest) :Response<String>

    @DELETE("preset/delete/{pid}")
    suspend fun deletePreset(@Path("pid") pid: Long) : Response<String>
}