package com.example.beep.network.api

import com.example.beep.data.dto.mypage.PresetRequest
import com.example.beep.data.dto.mypage.PresetResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface PresetApi {
    @GET("preset/find/{uid}")
    suspend fun getUserPreset(@Path("uid") uid: Int): List<PresetResponse>

    @POST("preset/save")
    suspend fun updatePreset(@Body preset: PresetRequest)

    @DELETE("preset/delete/{pid}")
    suspend fun deletePreset(@Path("pid") pid: Int)
}