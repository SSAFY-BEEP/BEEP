package com.example.beep.network.api

import com.example.beep.data.dto.mainpage.AddressRequest
import com.example.beep.data.dto.mainpage.AddressResponse
import retrofit2.http.*

interface AddressApi {
    @GET("/api/phonebook")
    suspend fun getUserAddress(): List<AddressResponse>

    @POST("/api/phonebook")
    suspend fun postUserAddress(@Body addressInfo: AddressRequest): String

    @DELETE("/api/phonebook/{phone}")
    suspend fun deleteUserAddress(@Path("phone") phone: String)

    @PATCH("/api/phonebook/{phone}")
    suspend fun patchUserAddress(@Path("phone") phone: String, @Body addressInfo: AddressRequest)
}
