package com.example.beep.di

import android.app.Application
import com.example.beep.util.SharedPreferencesUtil
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    companion object {
        lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    }

    override fun onCreate() {
        super.onCreate()

        sharedPreferencesUtil = SharedPreferencesUtil(this)
    }
}