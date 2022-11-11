package com.example.beep.data.repository

import com.example.beep.data.datasource.MessageDataSource
import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.message.MessageRequest
import com.example.beep.data.dto.message.MessageResponse
import com.example.beep.util.ResultType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class MessageRepository @Inject constructor(private val messageDataSource: MessageDataSource) {
    suspend fun getReceiveMessage(type: Int): ResultType<BaseResponse<List<MessageResponse>>> =
        try {
            val result = messageDataSource.getReceiveMessage(type)
            if (result.status == "OK") {
                ResultType.Success(result)
            } else {
                ResultType.Fail(result)
            }
        } catch (e: Exception) {
            ResultType.Error(e)
        }

    suspend fun getSendMessage(): ResultType<BaseResponse<List<MessageResponse>>> = try {
        val result = messageDataSource.getSendMessage()
        if (result.status == "OK") {
            ResultType.Success(result)
        } else {
            ResultType.Fail(result)
        }
    } catch (e: Exception) {
        ResultType.Error(e)
    }


    suspend fun changeTag(id: Long, tag: String): ResultType<BaseResponse<String>> = try {
        val response = messageDataSource.changeTag(id, tag)
        if (response.status == "OK") {
            ResultType.Success(response)
        } else {
            ResultType.Fail(response)
        }
    } catch (e: Exception) {
        ResultType.Error(e)
    }


    suspend fun deleteMessage(messageId: Long): ResultType<BaseResponse<String>> = try {
        val response = messageDataSource.deleteMessage(messageId)
        if (response.status == "OK") {
            ResultType.Success(response)
        } else {
            ResultType.Fail(response)
        }
    } catch (e: Exception) {
        ResultType.Error(e)
    }

    suspend fun blockMessage(messageId: Long): ResultType<BaseResponse<String>> = try {
        val response = messageDataSource.blockMessage(messageId)
        if (response.status == "OK") {
            ResultType.Success(response)
        } else {
            ResultType.Fail(response)
        }
    } catch (e: Exception) {
        ResultType.Error(e)
    }
}