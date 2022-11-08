package com.example.beep.util

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader(
            "Authorization",
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTA3NDc5NDE4NSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2Njc5ODE3NDF9.KXOq_1cYyBg5beLxLOhOiIGKHfme4sSuyGtEIpZJWGX-zFxZud1PYjXN6w9RGR6b3Svxv7shT6ZUx-LYMUqbtA"
        )

        return chain.proceed(requestBuilder.build())
    }
}