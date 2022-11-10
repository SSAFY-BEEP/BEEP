package com.example.beep.data.datasource

import com.example.beep.data.dto.mainpage.AddressRequest
import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.data.dto.mainpage.BaseResponse
import com.example.beep.data.dto.message.MessageRequest
import com.example.beep.network.api.AddressApi
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressDataSource @Inject constructor(private val addressApi: AddressApi){
    fun getUserAddress(): Flow<BaseResponse<List<AddressResponse>>> = flow {
        emit(addressApi.getUserAddress())
    }
    fun postUserAddress(phone: String, name: String,): Flow<BaseResponse<String>> = flow {
        val newAddressList = listOf(AddressRequest(phone, name))
        emit(addressApi.postUserAddress(newAddressList))
    }
    fun deleteUserAddress(phone: String): Flow<BaseResponse<String>> = flow {
        emit(addressApi.deleteUserAddress(phone))
    }
    fun patchUserAddress(apiPhone: String, phone: String, name: String,): Flow<BaseResponse<AddressResponse>> = flow {
        emit(addressApi.patchUserAddress(apiPhone, AddressRequest(phone, name)))
    }
}
