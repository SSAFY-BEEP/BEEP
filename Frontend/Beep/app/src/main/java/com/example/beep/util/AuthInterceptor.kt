package com.example.beep.util

import android.content.SharedPreferences
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sharedPref: SharedPreferences
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var token = runBlocking {
            sharedPref.getString(JWT,"")!!
        }

        val requestBuilder = chain.request().newBuilder()
            .addHeader(JWT, token)
            .build()

        return chain.proceed(requestBuilder)
    }
}