package com.example.beep.data.repository

import com.example.beep.data.datasource.AddressDataSource
import com.example.beep.data.dto.mainpage.AddressRequest
import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.data.dto.mainpage.BaseResponse
import com.example.beep.util.ResultType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddressRepository @Inject constructor(private val addressDataSource: AddressDataSource) {
    fun getUserAddress(): Flow<ResultType<BaseResponse<List<AddressResponse>>>> =
        flow { addressDataSource.getUserAddress().collect {
            if (it.status == "OK") {
                emit(ResultType.Success(it))
            } else {
                emit(ResultType.Fail(it))
            }
        } }
    fun postUserAddress(phone: String, name: String,): Flow<ResultType<BaseResponse<String>>> =
        flow { addressDataSource.postUserAddress(phone, name).collect {
            if (it.status == "OK") {
                emit(ResultType.Success(it))
            } else {
                emit(ResultType.Fail(it))
            }
        }
        }.catch {
            e-> emit(ResultType.Error(e))
        }
    fun deleteUserAddress(phone: String): Flow<ResultType<BaseResponse<String>>> =
        flow { addressDataSource.deleteUserAddress(phone).collect {
            if (it.status == "OK") {
                emit(ResultType.Success(it))
            } else {
                emit(ResultType.Fail(it))
            }
        } }
    fun patchUserAddress(apiPhone: String, phone: String, name: String,): Flow<ResultType<BaseResponse<AddressResponse>>> =
        flow { addressDataSource.patchUserAddress(apiPhone, phone, name).collect {
            if (it.status == "OK") {
                emit(ResultType.Success(it))
            } else {
                emit(ResultType.Fail(it))
            }
        } }
}