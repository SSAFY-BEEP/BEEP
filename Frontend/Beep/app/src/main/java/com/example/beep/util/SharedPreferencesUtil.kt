package com.example.beep.util

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.text.font.FontFamily

class SharedPreferencesUtil(context: Context) {
    private val sharedPreferencesName = "store_preference"
    private val preferences: SharedPreferences =
        context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)

    // font 저장
    fun saveFont(font:Int) {
        val editor = preferences.edit()
        editor.putInt("font", font)
        editor.apply()
    }

    // font 불러오기
    fun getFont(): Int {
        return preferences.getInt("font", 1)
    }

    // theme 저장
    fun saveTheme(theme: Int) {
        val editor = preferences.edit()
        editor.putInt("theme", theme)
        editor.apply()
    }

    // theme 불러오기
    fun getTheme(): Int {
        return preferences.getInt("theme", 1)
    }

    // 토큰 저장
    fun saveToken(token: String) {
        val editor = preferences.edit()
        editor.putString("token", token)
        editor.apply()
    }

    // 토큰 불러오기
    fun getToken(): String? {
        return preferences.getString("token", null)
    }

    // preference 지우기
    fun deleteToken() {
        val editor = preferences.edit()
        editor.remove("token")
        editor.apply()
    }

    // fcm 토큰 저장하기
    fun saveFcmToken(fcmToken: String) {
        val editor = preferences.edit()
        editor.putString("fcmToken", fcmToken)
        editor.apply()
    }

    // fcm 토큰 불러오기
    fun getFcmToken(): String? {
        return preferences.getString("fcmToken", null)
    }
}