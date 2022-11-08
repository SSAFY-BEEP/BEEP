package com.example.beep.data.datasource

import com.example.beep.data.dto.mainpage.AddressRequest
import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.network.api.AddressApi
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressDataSource @Inject constructor(private val addressApi: AddressApi){
    fun getUserAddress(): Flow<List<AddressResponse>> = flow {
        emit(addressApi.getUserAddress())
    }
    fun postUserAddress(phone: String, name: String,): Flow<String> = flow {
        emit(addressApi.postUserAddress(AddressRequest(phone, name)))
    }
}
