package com.example.beep.network.api

import com.example.beep.data.dto.mainpage.AddressRequest
import com.example.beep.data.dto.mainpage.AddressResponse
import retrofit2.http.*

interface AddressApi {
    @GET("/api/phonebook")
    suspend fun getUserAddress(): List<AddressResponse>

    @POST("/api/phonebook")
    suspend fun updateAddress(@Body addressInfo: AddressRequest)

    @DELETE("/api/phonebook/{phone}")
    suspend fun deleteAddress(@Path("phone") phone: String)

    @PATCH("/api/phonebook/{phone}")
    suspend fun patchAddress(@Path("phone") phone: String, @Body addressInfo: AddressRequest)
}
