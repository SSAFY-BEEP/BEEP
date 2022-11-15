package com.example.beep.util

import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class VoicePlayer {
    companion object {
        @Volatile
        private var instance: MediaPlayer? = null

        @JvmStatic
        fun getInstance(): MediaPlayer =
            instance ?: synchronized(this) {
                instance ?: MediaPlayer().also {
                    instance = it
                    Log.d("VoicePlayer", "VoicePlayerInitialized")
                }
            }

        @JvmStatic
        fun nullInstance() {
            instance?.apply {
                stop()
                release()
            }
            instance = null
        }

        fun hasInstance() = instance != null

    }
}