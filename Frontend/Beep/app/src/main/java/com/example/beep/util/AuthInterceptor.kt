package com.example.beep.util

import com.example.beep.di.MainApplication
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = MainApplication.sharedPreferencesUtil.getToken()

        requestBuilder.addHeader(
            "Authorization",
            "Bearer $token"
        )

        return chain.proceed(requestBuilder.build())
    }
}
