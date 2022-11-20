package com.example.beep.ui.home

import android.content.Context
import android.os.Build
import android.os.Vibrator
import android.os.VibratorManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beep.util.SoundEffectPlayer
import com.example.beep.util.SoundEffectType
import com.example.beep.util.keyboardLongVibration
import com.example.beep.util.keyboardVibration

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalFoundationApi::class)
@ExperimentalComposeUiApi
@Composable
fun KeyboardButton(
    modifier: Modifier = Modifier,
    paint: Painter,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .width(98.dp)
            .height(50.dp)
            .combinedClickable(
                onClick = {
                    onClick()
                    vibrator.vibrate(keyboardVibration)
                },
                onLongClick = {onLongClick()
                    vibrator.vibrate(keyboardLongVibration)
                }
            )
//            .clickable {
//                onClick()
//            }
            .then(modifier)
    ) {
        Image(
            painter = paint,
            contentDescription = "description",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}