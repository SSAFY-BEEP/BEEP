package com.example.beep.ui.home

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.util.SoundEffectPlayer
import com.example.beep.util.SoundEffectType
import com.example.beep.util.VoicePlayer
import com.example.beep.util.VoicePlayer.Companion.stopPlaying
import com.example.beep.util.VoiceRecorder
import com.example.beep.util.VoiceRecorder.Companion.stopRecording
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.util.*

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BbibbiDoRecord(
    toSendMsg: () -> Unit,
    toAskRecord: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val voicePermissionState = rememberPermissionState(
        Manifest.permission.RECORD_AUDIO
    )
    val context = LocalContext.current
    var filepath = context.cacheDir.absolutePath + "/temp.3gp"
    val currentState = homeViewModel.recordMessageState

    DisposableEffect(key1 = Unit) {
        homeViewModel.playGreeting()
        VoiceRecorder.nullInstance()
        VoiceRecorder.getInstance(context)
        File(filepath)

        onDispose {
            VoiceRecorder.nullInstance()
            VoicePlayer.nullInstance()
        }
    }
    Box(
        modifier = modifier
            .width(320.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
        contentAlignment = Alignment.TopCenter
        ) {
        DoRecordScreen(
            currentState = currentState,
            messageTime = formatSecond(homeViewModel.time),
            duration = formatSecond(homeViewModel.fileLength),
            modifier = Modifier
                .offset(0.dp, 5.dp)
        )
        Spacer(modifier = modifier.height(23.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset(0.dp, 42.dp)
                .height(64.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            CancelBtn(onClick = {
                SoundEffectPlayer.playSoundEffect(SoundEffectType.BeepBtn)
                when (currentState) {
                    RecordMessageState.Before, RecordMessageState.Greeting -> {
                        toAskRecord()
                        stopPlaying()
                        homeViewModel.stopTimer()
                        homeViewModel.recordMessageState = RecordMessageState.Greeting
                    }
                    RecordMessageState.Recording -> {
                        stopRecording()
                        homeViewModel.stopTimer()
                        homeViewModel.recordMessageState = RecordMessageState.Finished
                    }
                    RecordMessageState.Finished -> {
                        resetRecording(context)
                        homeViewModel.stopTimer()
                        homeViewModel.recordMessageState = RecordMessageState.Before
                    }
                    RecordMessageState.Playing -> {
                        stopPlaying()
                        homeViewModel.stopTimer()
                        homeViewModel.recordMessageState = RecordMessageState.Finished
                    }
                    else -> {}
                }
            })
            LeftBtn(onClick = {
                SoundEffectPlayer.playSoundEffect(SoundEffectType.BeepBtn)
                when (currentState) {
                    RecordMessageState.Before -> {}
                    RecordMessageState.Recording -> {}
                    RecordMessageState.Finished -> {}
                    RecordMessageState.Playing -> {}
                    else -> {}
                }
            })
            RightBtn(onClick = {
                SoundEffectPlayer.playSoundEffect(SoundEffectType.BeepBtn)
                when (currentState) {
                    RecordMessageState.Before -> {}
                    RecordMessageState.Recording -> {}
                    RecordMessageState.Finished -> {
                        startPlayingVoiceMessage(
                            filepath = filepath,
                            changeCurrentState = {
                                homeViewModel.recordMessageState = RecordMessageState.Finished
                            },
                            onPrepared = { duration: Int -> homeViewModel.fileLength = duration })
                        homeViewModel.startTimer()
                        homeViewModel.recordMessageState = RecordMessageState.Playing
                    }
                    RecordMessageState.Playing -> {}
                    else -> {}
                }
            })
            ConfirmBtn(onClick = {
                SoundEffectPlayer.playSoundEffect(SoundEffectType.BeepBtn)
                when (currentState) {
                    RecordMessageState.Greeting -> {
                        homeViewModel.stopGreeting()
                        homeViewModel.stopTimer()
                        homeViewModel.recordMessageState = RecordMessageState.Before
                    }
                    RecordMessageState.Before -> {
                        if (!voicePermissionState.status.isGranted) {
                            voicePermissionState.launchPermissionRequest()
                        }
                        if (voicePermissionState.status.isGranted) {
                            if (File(filepath).exists()) {
                                File(filepath).delete()
                            }
                            homeViewModel.fileLength = 31
                            VoiceRecorder.startRecording(context, filepath)
                            homeViewModel.startTimer()
                            homeViewModel.recordMessageState = RecordMessageState.Recording
                        }
                    }
                    RecordMessageState.Recording -> {
                        VoiceRecorder.stopRecording()
                        homeViewModel.stopTimer()
                        homeViewModel.recordMessageState = RecordMessageState.Finished
                    }
                    RecordMessageState.Finished -> {
                        toSendMsg()
                        homeViewModel.recordMessageState = RecordMessageState.Before
                    }
                    RecordMessageState.Playing -> {
                        stopPlaying()
                        homeViewModel.stopTimer()
                        homeViewModel.recordMessageState = RecordMessageState.Finished
                    }
                    else -> {}
                }
            })
        }
    }
}

fun startPlayingVoiceMessage(
    filepath: String,
    changeCurrentState: () -> Unit,
    onPrepared: (Int) -> Unit
) {
    VoicePlayer.nullInstance()
    VoicePlayer.getInstance().apply {
        setDataSource(filepath)
        prepare()
        setOnPreparedListener {
            onPrepared(it.duration / 1000)
        }
        setOnCompletionListener {
            stopPlaying()
            changeCurrentState()
        }
    }.start()
}


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ConfirmBtn(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .offset(214.dp, 20.dp)
            .width(83.dp)
            .height(64.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent.copy(0.0F)),
        shape = RoundedCornerShape(65.dp, 20.dp, 50.dp, 0.dp),
        onClick = onClick,
        elevation = null
    ) {
    }
}

@Composable
fun RightBtn(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .offset(154.dp, 20.dp)
            .width(68.dp)
            .height(42.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent.copy(0.0F)),
        onClick = onClick,
        elevation = null,
        shape = RoundedCornerShape(5.dp, 0.dp, 40.dp, 5.dp)
    ) {

    }
}

@Composable
fun LeftBtn(
    modifier: Modifier = Modifier, onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .offset(93.dp, 20.dp)
            .width(60.dp)
            .height(42.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent.copy(0.0F)),
        onClick = onClick,
        elevation = null,
        shape = RoundedCornerShape(5.dp)
    ) {

    }
}

@Composable
fun CancelBtn(
    modifier: Modifier = Modifier, onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .offset(24.dp, 20.dp)
            .width(69.dp)
            .height(42.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent.copy(0.0F)),
        onClick = onClick,
        elevation = null,
        shape = RoundedCornerShape(5.dp, 5.dp, 5.dp, 30.dp)
    ) {

    }
}

@Composable
fun DoRecordScreen(
    modifier: Modifier = Modifier,
    currentState: RecordMessageState,
    messageTime: String,
    duration: String
) {

    when (currentState) {
        RecordMessageState.Loading -> {
            Text(text = "로딩중..", modifier = Modifier.padding(0.dp, 5.dp))
        }
        RecordMessageState.NoIntroduce -> {
            Text(text = "상대의 인사말이 없습니다.", modifier = Modifier.padding(0.dp, 5.dp))
        }
        RecordMessageState.Greeting -> {
            Text(text = "인사말 재생중...", fontSize = 16.sp)
            Text(text = "$messageTime/$duration", fontSize = 11.sp, modifier = Modifier.padding(0.dp, 23.dp), color = Color.DarkGray)
        }
        RecordMessageState.Before -> {
            Text(text = "\\ 취소    ● 녹음 시작", fontSize = 17.sp, modifier = Modifier.padding(0.dp, 5.dp))
        }
        RecordMessageState.Recording -> {
            Text(text = "녹음중 $messageTime/$duration", fontSize = 17.sp, modifier = Modifier.padding(0.dp, 5.dp))
        }
        RecordMessageState.Finished -> {
            Text(text = "\\ 재녹음  ▶ 재생  ● 전송", fontSize = 17.sp, modifier = Modifier.padding(0.dp, 5.dp))
        }
        RecordMessageState.Playing -> {
            Text(text = "재생중 $messageTime/$duration", fontSize = 17.sp, modifier = Modifier.padding(0.dp, 5.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
fun resetRecording(context: Context) {
    VoiceRecorder.nullInstance()
    VoiceRecorder.getInstance(context)
}

fun formatSecond(second: Int): String {
    if (second < 10) {
        return "00:0$second"
    }
    return "00:$second"
}