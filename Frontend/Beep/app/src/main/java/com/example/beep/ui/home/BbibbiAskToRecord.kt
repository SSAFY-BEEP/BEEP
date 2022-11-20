package com.example.beep.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.util.SoundEffectPlayer
import com.example.beep.util.SoundEffectType

@Composable
fun BbibbiAskToRecord(
    toPutMsg: () -> Unit,
    toSendMsg: () -> Unit,
    toRecord: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(),
    ) {
    Log.d("PageMove", "Moved to AskToRecord")
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    Log.d("screenwidth", screenWidth.toString())
    var go by remember { mutableStateOf(true) }

    var r = 0
    var l = 0
    if (go) {
        r = 140
        l = 0
    } else {
        r = 0
        l = 60
    }

    Button(
        // 메시지 입력 페이지로(메시지 내용 살아있음)
        onClick = {
            SoundEffectPlayer.playSoundEffect(SoundEffectType.BeepBtn)
            /* cancel 버튼 */
            toPutMsg()
        },
        modifier = Modifier
            .offset(24.dp, 135.dp)
            .width(69.dp)
            .height(42.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta.copy(0.0F)),
        shape = RoundedCornerShape(5.dp, 5.dp, 5.dp, 30.dp)
    ) {
    }

    Button(
        // <-
        onClick = {
            SoundEffectPlayer.playSoundEffect(SoundEffectType.BeepBtn)
            go = !go
        },
        modifier = Modifier
            .width(60.dp)
            .offset(93.dp, 135.dp)
            .height(42.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta.copy(0.0F)),
        shape = RoundedCornerShape(5.dp)
    ) {

    }

    Button(
        // ->
        onClick = {
            SoundEffectPlayer.playSoundEffect(SoundEffectType.BeepBtn)
            go = !go
        },
        modifier = Modifier
            .width(68.dp)
            .offset(154.dp, 135.dp)
            .height(42.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta.copy(0.0F)),
        shape = RoundedCornerShape(5.dp, 0.dp, 40.dp, 5.dp)
    ) {

    }

    Button(
        // 녹음페이지로
        onClick = {
            SoundEffectPlayer.playSoundEffect(SoundEffectType.BeepBtn)
            /* go버튼 */
                  if (go) {
                      // 녹음페이지
                      toRecord()
                  } else {
                      toSendMsg()
                  }
        },
        modifier = Modifier
            .width(83.dp)
            .offset(214.dp, 112.dp)
            .height(64.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta.copy(0.0F)),
        contentPadding = PaddingValues(0.dp),
        shape = RoundedCornerShape(65.dp, 20.dp, 50.dp, 0.dp)
    ) {

    }



    Text(
        text = "음성 메시지 보내기",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(top = 35.dp),
        fontSize = 17.sp,
        fontFamily = galmurinineFont
    )
    Row(
        modifier = Modifier
            .offset(0.dp, 63.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        Button(
            // 녹음페이지로
            onClick = {
                /* go버튼 */
            },
            modifier = Modifier
                .width(80.dp)
                .padding(0.dp, 0.dp, 20.dp, 0.dp)
                .height(30.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta.copy(0.0F)),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = "YES",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(0.dp),
                fontSize = 15.sp,
                fontFamily = galmurinineFont
            )
        }
        Button(
            // 메시지 보낼까 페이지로
            onClick = {
                /* go버튼 */
                toSendMsg()
            },
            modifier = Modifier
                .width(80.dp)
                .padding(20.dp, 0.dp, 0.dp, 0.dp)
                .height(30.dp),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta.copy(0.0F)),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = "NO ",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(0.dp),
                fontSize = 15.sp,
                fontFamily = galmurinineFont
            )
        }
    }


    Text(
        text = ">",
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(l.dp, 66.dp, r.dp, 0.dp),
        fontSize = 15.sp,
        fontFamily = galmurinineFont
    )
}