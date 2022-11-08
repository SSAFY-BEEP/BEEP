package com.example.beep.util

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader(
            "Authorization",
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTA3NDc5NDE4NSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2Njc4OTQ4NTF9.frK2uebvushNN-yeGhBTHfkIiFx3GnWMlndjr937gHKMmWqo-1gMAJ7hxquHn8ZsqZDIZcQyssiGa5niNbXLMw"
        )

        return chain.proceed(requestBuilder.build())
    }
}