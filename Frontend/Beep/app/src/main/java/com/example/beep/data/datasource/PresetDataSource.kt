package com.example.beep.data.datasource

import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.mypage.PresetRequest
import com.example.beep.data.dto.mypage.PresetResponse
import com.example.beep.network.api.PresetApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PresetDataSource @Inject constructor(private val presetApi: PresetApi){
    fun getUserPreset(uid: Long, part : Int): Flow<BaseResponse<List<PresetResponse>>> = flow {
        emit(presetApi.getUserPreset(uid, part))
    }
    fun getUserPresetByToken(part : Int): Flow<BaseResponse<List<PresetResponse>>> = flow {
        emit(presetApi.getUserPresetByToken(part))
    }
    fun updatePreset(number : Int, part : Int, content : String): Flow<BaseResponse<String>> = flow {
        val prestRequest = PresetRequest(number, part, content)
        emit(presetApi.updatePreset(prestRequest))
    }
    fun deletePreset(pid: Long): Flow<BaseResponse<String>> = flow {
        emit(presetApi.deletePreset(pid))
    }
}