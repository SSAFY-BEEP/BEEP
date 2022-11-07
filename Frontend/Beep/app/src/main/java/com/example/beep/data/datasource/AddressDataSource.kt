package com.example.beep.data.datasource

import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.network.api.AddressApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressDataSource @Inject constructor(private val addressApi: AddressApi){
    fun getUserAddress(): Flow<List<AddressResponse>> = flow {
        emit(addressApi.getUserAddress())
    }
}
