package com.example.beep.network.api

import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.mypage.PresetRequest
import com.example.beep.data.dto.mypage.PresetResponse
import retrofit2.Response
import retrofit2.http.*

interface PresetApi {
    @GET("preset/find")
    suspend fun getUserPreset(): BaseResponse<Any>

    @GET("preset/find")
    suspend fun getUserPresetByToken(): BaseResponse<Any>

    @POST("preset/save")
    suspend fun updatePreset(@Body preset: PresetRequest) :BaseResponse<Any>

    @DELETE("preset/delete/{pid}")
    suspend fun deletePreset(@Path("pid") pid: Long) : BaseResponse<Any>
}