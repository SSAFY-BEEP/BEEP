package com.example.beep.util

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.example.beep.R

enum class SoundEffectType {
    BeepBtn
}

class SoundEffectPlayer() {
    companion object {
        @Volatile
        private var btnBeepPlayer: MediaPlayer? = null

        @JvmStatic
        fun readyEffects(context: Context) {
            btnBeepPlayer = MediaPlayer.create(context, R.raw.beep_btn_click)
        }

        @JvmStatic
        fun playSoundEffect(type: SoundEffectType) {
            when (type) {
                SoundEffectType.BeepBtn -> {
                    btnBeepPlayer?.seekTo(0)
                    btnBeepPlayer?.start()
                }
            }
        }

        @JvmStatic
        fun releaseEffects() {
            btnBeepPlayer?.release()
        }

    }



}