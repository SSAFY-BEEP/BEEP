package com.example.beep.data.repository.datasource

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
    fun getUserPreset(): Flow<Response<List<PresetResponse>>> = flow {
        emit(presetApi.getUserPreset())
    }
    fun getUserPresetByToken(): Flow<Response<List<PresetResponse>>> = flow {
        emit(presetApi.getUserPresetByToken())
    }
    fun updatePreset(number : Int, part : Int, content : String): Flow<Response<String>> = flow {
        val prestRequest = PresetRequest(number, part, content);
        emit(presetApi.updatePreset(prestRequest))
    }
    fun deletePreset(pid: Long): Flow<Response<String>> = flow {
        emit(presetApi.deletePreset(pid))
    }
}