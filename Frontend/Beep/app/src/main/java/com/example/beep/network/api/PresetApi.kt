package com.example.beep.network.api

import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.mypage.PresetRequest
import com.example.beep.data.dto.mypage.PresetResponse
import retrofit2.Response
import retrofit2.http.*

interface PresetApi {
    @GET("preset/find/{uid}/{part}")
    suspend fun getUserPreset(@Path("uid") uid: Long, @Path("part") part: Int): BaseResponse<List<PresetResponse>>

    @GET("preset/findToken/{part}")
    suspend fun getUserPresetByToken(@Path("part") part : Int): BaseResponse<List<PresetResponse>>

    @POST("preset/save")
    suspend fun updatePreset(@Body preset: PresetRequest) :BaseResponse<String>

    @DELETE("preset/delete/{pid}")
    suspend fun deletePreset(@Path("pid") pid: Long) : BaseResponse<String>
}