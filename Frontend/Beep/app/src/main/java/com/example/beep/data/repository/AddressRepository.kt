package com.example.beep.data.repository

import com.example.beep.data.repository.datasource.AddressDataSource
import com.example.beep.data.dto.mainpage.AddressRequest
import com.example.beep.data.dto.mainpage.AddressResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddressRepository @Inject constructor(private val addressDataSource: AddressDataSource) {
    fun getUserAddress(): Flow<List<AddressResponse>> =
        flow { addressDataSource.getUserAddress().collect { emit(it)} }
    fun postUserAddress(phone: String, name: String,): Flow<String> =
        flow { addressDataSource.postUserAddress(phone, name).collect { emit(it)} }
    fun patchUserAddress(apiPhone: String, phone: String, name: String,): Flow<AddressResponse> =
        flow { addressDataSource.patchUserAddress(apiPhone, phone, name).collect { emit(it)} }
}