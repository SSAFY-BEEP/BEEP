package com.example.beep.network.api

import com.example.beep.data.dto.BaseResponse
import com.example.beep.data.dto.mypage.UserInfoResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserApi {
    @PATCH("user/alarm/{number}")
    suspend fun setSound(@Path("number") number: Int): BaseResponse<String>

    @PATCH("user/font/{number}")
    suspend fun setFont(@Path("number") number: Int): BaseResponse<String>

    @PATCH("user/theme/{number}")
    suspend fun setTheme(@Path("number") number: Int): BaseResponse<String>

    @PATCH("user/engrave/{engrave}")
    suspend fun setEngrave(@Path("engrave") engrave: String): BaseResponse<String>

    @GET("user")
    suspend fun getUserInfo() : BaseResponse<UserInfoResponse>
}