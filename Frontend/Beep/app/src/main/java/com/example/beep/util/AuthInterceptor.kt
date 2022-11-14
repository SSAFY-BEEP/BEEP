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
            "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIwMTA3NDc5NDE4NSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2Njg0NzM2MDB9.Ev_N6kL4F8e9EaeQS0e9yFxix0EYNTJPnmG2gGgFaZZsN-FdUEjH-5cCCVN8k5y-fNHQ1XSlkGIBME8HlI_92Q"
        )

        return chain.proceed(requestBuilder.build())
    }
}