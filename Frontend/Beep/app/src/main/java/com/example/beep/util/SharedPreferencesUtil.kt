package com.example.beep.util

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {
    private val sharedPreferencesName = "store_preference"
    private val preferences: SharedPreferences =
        context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)


    // 토큰 저장
    fun saveToken(token: String) {
        val editor = preferences.edit()
        editor.putString("token", token)
        editor.commit()
    }

    // 토큰 불러오기
    fun getToken(): String? {
        val token = preferences.getString("token", null)
        return token
    }

    // preference 지우기
    fun deleteToken() {
        val editor = preferences.edit()
        editor.remove("token")
        editor.apply()
    }
}