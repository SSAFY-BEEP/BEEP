package com.example.beep.data.repository

import com.example.beep.data.datasource.PresetDataSource
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.mypage.PresetResponse
import com.example.beep.util.ResultType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PresetRepository @Inject constructor(private val presetDataSource: PresetDataSource) {
    fun getUserPreset(uid: Long, part : Int): Flow<ResultType<BaseResponse<List<PresetResponse>>>> = flow {
        presetDataSource.getUserPreset(uid, part).collect {
            if (it.status == "OK") {
                emit(ResultType.Success(it))
            } else {
                emit(ResultType.Fail(it))
            }
        }
    }.catch { e -> emit(ResultType.Error(e)) }

    fun getUserPresetByToken(part : Int): Flow<ResultType<BaseResponse<List<PresetResponse>>>> = flow {
        presetDataSource.getUserPresetByToken(part).collect {
            if (it.status == "OK") {
                emit(ResultType.Success(it))
            } else {
                emit(ResultType.Fail(it))
            }
        }
    }.catch { e -> emit(ResultType.Error(e)) }

    fun updatePreset(number: Int, part: Int, content: String): Flow<ResultType<BaseResponse<String>>> =
        flow {
            presetDataSource.updatePreset(number, part, content).collect {
                if (it.status == "OK") {
                    emit(ResultType.Success(it))
                } else {
                    emit(ResultType.Fail(it))
                }
            }
        }.catch { e -> emit(ResultType.Error(e)) }

    fun deletePreset(pid: Long): Flow<ResultType<BaseResponse<String>>> =
        flow {
            presetDataSource.deletePreset(pid).collect {
                if (it.status == "OK") {
                    emit(ResultType.Success(it))
                } else {
                    emit(ResultType.Fail(it))
                }
            }
        }.catch { e -> emit(ResultType.Error(e)) }
}

