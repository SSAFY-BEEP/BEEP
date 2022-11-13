package com.example.beep.util

import com.example.beep.di.MainApplication
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = MainApplication.sharedPreferencesUtil.getToken()

        requestBuilder.addHeader(
            "Authorization",
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTA3NDc5NDE4NSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2Njg0MjI2MjF9.z2jTbO9Pjr9dn7S55Hy-xWvfJWl8tv-HzBiXVHUCIciB53n_tRze_fvEvt2VqB4sXfIMwYkPkwUAdal6aUXdOg"
        )

        return chain.proceed(requestBuilder.build())
    }
}