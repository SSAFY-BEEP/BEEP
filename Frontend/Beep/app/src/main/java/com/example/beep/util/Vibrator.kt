package com.example.beep.util

import android.os.Build
import android.os.VibrationEffect
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.Q)
val keyboardVibration: VibrationEffect =
    VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)

@RequiresApi(Build.VERSION_CODES.Q)
val keyboardLongVibration: VibrationEffect =
    VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
