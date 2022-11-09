package com.example.beep.data.repository.datasource

import com.example.beep.data.dto.mypage.PresetRequest
import com.example.beep.data.dto.mypage.PresetResponse
import com.example.beep.network.api.PresetApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PresetDataSource @Inject constructor(private val presetApi: PresetApi){
    fun getUserPreset(uid: Int): Flow<List<PresetResponse>> = flow {
        emit(presetApi.getUserPreset(uid))
    }
//    fun saveUserPreset(presetRequest: PresetRequest): Flow<> = flow {
//        emit(presetApi.updatePreset(presetRequest))
//    }
//    fun deleteUserPreset(pid: Int): Flow<> = flow {
//        emit(presetApi.deletePreset(pid))
//    }
}