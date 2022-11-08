package com.example.beep.util

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader(
            "Authorization",
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTA3NDc5NDE4NSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2Njc5ODIwMTJ9.7BusIONk9KTUWxiLHy4psARoKus8B6zwdUXVxnkfE33_KMKISItWV2FZaYnaLwTiUbcylriTGsXQleyMf-AKNg"
        )

        return chain.proceed(requestBuilder.build())
    }
}