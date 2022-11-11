package com.example.beep.network.api

import com.example.beep.data.dto.BaseResponse
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserApi {
    @PATCH("user/alarm/{number}")
    suspend fun setSound(@Path("number") number: Integer): BaseResponse<Any>

    @PATCH("user/font/{number}")
        suspend fun setFont(@Path("number") number: Integer): BaseResponse<Any>

    @PATCH("user/theme/{number}")
        suspend fun setTheme(@Path("number") number: Integer): BaseResponse<Any>

    @PATCH("user/engrave/{engrave}")
    suspend fun setEngrave(@Path("engrave") engrave: String): BaseResponse<Any>
}