package com.example.beep.data.repository

import com.example.beep.data.dto.message.Message24Response
import com.example.beep.data.datasource.Message24DataSource
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.message.Message24Request
import com.example.beep.util.ResultType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class Message24Repository @Inject constructor(private val message24DataSource: Message24DataSource) {
    fun getReceiveMsg24(): Flow<ResultType<BaseResponse<List<Message24Response>>>> =
        flow {
            emit(ResultType.Loading)
            message24DataSource.getReceiveMsg24().collect {
                if (it.status == "OK")
                    emit(ResultType.Success(it))
                else
                    emit(ResultType.Fail(it))
            }
        }.catch { e -> emit(ResultType.Error(e)) }

    fun getSendMsg24(): Flow<ResultType<BaseResponse<List<Message24Response>>>> =
        flow {
            emit(ResultType.Loading)
            message24DataSource.getSendMsg24().collect {
                if (it.status == "OK")
                    emit(ResultType.Success(it))
                else
                    emit(ResultType.Fail(it))
            }
        }.catch { e -> emit(ResultType.Error(e)) }

    fun getMsg24(id: String): Flow<ResultType<BaseResponse<Message24Response>>> =
        flow {
            emit(ResultType.Loading)
            message24DataSource.getMsg24(id).collect {
                if (it.status == "OK")
                    emit(ResultType.Success(it))
                else
                    emit(ResultType.Fail(it))
            }
        }.catch { e -> emit(ResultType.Error(e)) }

    fun sendMsg(
        file: MultipartBody.Part?,
        message: Message24Request
    ): Flow<ResultType<BaseResponse<String>>> =
        flow {
            message24DataSource.sendMsg(file, message).collect {
                if (it.status == "OK")
                    emit(ResultType.Success(it))
                else emit(
                    ResultType.Fail(it)
                )
            }
        }.catch { e -> emit(ResultType.Error(e)) }

    fun saveMsg(messageId: String): Flow<ResultType<BaseResponse<String>>> =
        flow {
            message24DataSource.saveMsg(messageId)
                .collect {
                    if (it.status == "OK")
                        emit(ResultType.Success(it))
                    else emit(ResultType.Fail(it))
                }
        }.catch { e -> emit(ResultType.Error(e)) }

    fun deleteMsg(messageId: String): Flow<ResultType<BaseResponse<String>>> =
        flow {
            message24DataSource.deleteMsg(messageId).collect {
                if (it.status == "OK")
                    emit(ResultType.Success(it))
                else emit(
                    ResultType.Fail(it)
                )
            }
        }.catch { e -> emit(ResultType.Error(e)) }

    fun blockMsg(messageId: String): Flow<ResultType<BaseResponse<String>>> =
        flow {
            message24DataSource.blockMsg(messageId).collect {
                if (it.status == "OK")
                    emit(ResultType.Success(it))
                else emit(
                    ResultType.Fail(it)
                )
            }
        }.catch { e -> emit(ResultType.Error(e)) }
}