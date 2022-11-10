package com.example.beep.network.api

import com.example.beep.data.dto.mainpage.AddressRequest
import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.data.dto.mainpage.BaseResponse
import retrofit2.http.*

interface AddressApi {
    @GET("phonebook")
    suspend fun getUserAddress(): BaseResponse<List<AddressResponse>>

    @POST("phonebook")
    suspend fun postUserAddress(@Body addressInfo: List<AddressRequest>): BaseResponse<String>

    @DELETE("phonebook/{phone}")
    suspend fun deleteUserAddress(@Path("phone") phone: String): BaseResponse<String>

    @PATCH("phonebook/{phone}")
    suspend fun patchUserAddress(@Path("phone") apiPhone: String, @Body addressInfo: AddressRequest): BaseResponse<AddressResponse>
}
