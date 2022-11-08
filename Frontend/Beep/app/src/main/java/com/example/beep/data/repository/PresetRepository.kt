package com.example.beep.data.repository

import com.example.beep.data.repository.datasource.PresetDataSource
import com.example.beep.data.dto.mypage.PresetResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PresetRepository @Inject constructor(private val presetDataSource: PresetDataSource) {
    fun getUserPreset(uid: Int): Flow<List<PresetResponse>> =
        flow { presetDataSource.getUserPreset(uid).collect { emit(it) } }
}