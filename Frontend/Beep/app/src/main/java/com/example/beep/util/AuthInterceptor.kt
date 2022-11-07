package com.example.beep.util

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader(
            "Authorization",
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTA3NDc5NDE4NSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2Njc4MDg2NjZ9.LCr4zLZ1kwTL0zI-vA1bjhK-ovAdCWV_q7DqY6AGv4EBwjOA-36VtrFSXv8lEQnZu8dFeoeZQSWaAzp7lCT4iw"
        )

        return chain.proceed(requestBuilder.build())
    }
}