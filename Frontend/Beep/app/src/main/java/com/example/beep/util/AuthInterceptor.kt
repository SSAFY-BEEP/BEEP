package com.example.beep.util

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader(
            "Authorization",
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTA3NDc5NDE4NSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2NjgwNjk1ODd9.8ReBmGUD0XuSX4GXgpcCJUR7pCiCUW02ErQhd5UPy8fSfQfwRnKPZXWsqoTsf9B37WQ7uiiiggn1kl7ePkg6xQ"
        )

        return chain.proceed(requestBuilder.build())
    }
}