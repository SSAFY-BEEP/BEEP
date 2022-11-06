package com.example.beep.util

import android.media.MediaPlayer
import android.os.Build
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
                }
            }
    }
}