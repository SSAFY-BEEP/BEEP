package com.example.beep.util

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import androidx.annotation.RequiresApi

class VoiceRecorder {
    companion object {
        @Volatile
        private var instance: MediaRecorder? = null

        fun getInstanceWithoutContext() = instance

        @RequiresApi(Build.VERSION_CODES.S)
        @JvmStatic
        fun getInstance(context: Context): MediaRecorder =
            instance ?: synchronized(this) {
                instance ?: MediaRecorder(context).also {
                    instance = it
                }
            }

        @JvmStatic
        fun nullInstance() {
            instance?.apply {
                reset()
                release()
            }
            instance = null
        }

        fun hasInstance() = instance != null
    }


}