package com.example.beep.data.repository

import com.example.beep.data.dto.mypage.PresetResponse
import com.example.beep.data.datasource.PresetDataSource
import com.example.beep.data.dto.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class PresetRepository @Inject constructor(private val presetDataSource: PresetDataSource) {
    fun getUserPreset(): Flow<BaseResponse<Any>> =
        flow { presetDataSource.getUserPreset().collect { emit(it) } }

    fun getUserPresetByToken(): Flow<BaseResponse<Any>> =
        flow { presetDataSource.getUserPresetByToken().collect { emit(it) } }

    fun updatePreset(number : Int, part : Int, content : String): Flow<BaseResponse<Any>> =
        flow { presetDataSource.updatePreset(number, part, content).collect { emit(it) }}

    fun deletePreset(pid: Long): Flow<BaseResponse<Any>> =
        flow { presetDataSource.deletePreset(pid).collect { emit(it) } }

}
