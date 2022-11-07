package com.example.beep.util

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader(
            "Authorization",
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTA3NDc5NDE4NSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2Njc4OTU5MjF9.aJF91bUKa1eeyv-f8iytJdOP8G8TkJ2wFayFCKlLk3qeu3uG8Ca91ztk6afBbnclJaQgPLpR9DobSb96s1F9dQ"
        )

        return chain.proceed(requestBuilder.build())
    }
}