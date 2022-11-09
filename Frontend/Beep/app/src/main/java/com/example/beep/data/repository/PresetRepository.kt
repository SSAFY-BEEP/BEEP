package com.example.beep.data.repository

import com.example.beep.data.dto.mypage.PresetResponse
import com.example.beep.data.datasource.PresetDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class PresetRepository @Inject constructor(private val presetDataSource: PresetDataSource) {
    fun getUserPreset(uid: Long): Flow<Response<List<PresetResponse>>> =
        flow { presetDataSource.getUserPreset(uid).collect { emit(it) } }

    fun getUserPresetByToken(): Flow<Response<List<PresetResponse>>> =
        flow { presetDataSource.getUserPresetByToken().collect { emit(it) } }

    fun updatePreset(uid : Long, number : Int, part : Int, content : String): Flow<Response<String>> =
        flow { presetDataSource.updatePreset(uid, number, part, content).collect { emit(it) }}

    fun deletePreset(pid: Long): Flow<Response<String>> =
        flow { presetDataSource.deletePreset(pid).collect { emit(it) } }

}
