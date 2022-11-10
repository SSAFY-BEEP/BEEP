package com.example.beep.network.api

import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.auth.*
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {

    @POST("user/signup")
    suspend fun signUp(@Body request: SignUpRequest): BaseResponse<SignUpResponse>

    @POST("user/login")
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    @PATCH("user/pw")
    suspend fun newPassword(@Body request: NewPasswordRequest): BaseResponse<String>

    @PATCH("user/withdrawal")
    suspend fun withdrawal(): BaseResponse<String>



}