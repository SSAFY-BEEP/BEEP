package com.example.beep.util

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader(
            "Authorization",
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTA3NDc5NDE4NSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2NjgwNDUwMzJ9.XZHXChhyRsHy6avC_UX7l20rwe_R1O9PHMcRSdHn9u4vaCv3WfzIy7HZI4LkRODEUIjGMzoDvj0EWkzb7jRGoA"
        )

        return chain.proceed(requestBuilder.build())
    }
}