package com.example.beep.ui.mypage

import android.media.AudioAttributes
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.example.beep.ui.message.RecordVoiceScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.util.S3_CONSTANT_URI
import com.example.beep.util.VoicePlayer
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun IntroduceScreen(viewModel: IntroduceViewModel = viewModel()) {
    var isPopupVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

//    val introduceUrlFlow by viewModel.introduceUriFlow.collectAsStateLifecycleAware(initial = "")

    LaunchedEffect(key1 = isPopupVisible) {
        println("LaunchedEffect Launched!!")
        viewModel.getIntroduce()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier) {
            Text(text = "GreetingSettingScreen")
            Text(text = viewModel.introduceUrl)
            VoicePlayer.nullInstance()
            IntroducePlayer(viewModel.introduceUrl)
            Button(onClick = { isPopupVisible = !isPopupVisible }) {
                Text(text = "자기소개 녹음")
            }
//            Text(text = introduceUrlFlow)
        }
        if (isPopupVisible) {
            RecordVoiceScreen(modifier = Modifier.offset(50.dp, 100.dp)) {
                isPopupVisible = !isPopupVisible
            }
        }
    }
}

@Composable
fun IntroducePlayer(
    audioUrl: String,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    var voiceLength by remember { mutableStateOf(0) }
    var isReady by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableStateOf(0f) }
    var cursor by remember { mutableStateOf(0) }

    if (!VoicePlayer.hasInstance()) {
        VoicePlayer.nullInstance()
        VoicePlayer.getInstance().apply {
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
            )
            setDataSource(S3_CONSTANT_URI + audioUrl)
            setOnPreparedListener {
                isReady = true
                voiceLength = it.duration
            }
            prepareAsync()
            setOnCompletionListener {
                if (voiceLength != 0) {
                    it.stop()
                    it.release()
                }
                isReady = true
            }
        }
    }
    Log.d("Render", "IntroducePlayer")

    DisposableEffect(key1 = lifecycleOwner) {
        Log.d("DisposableEvent", "DisposableEvent")
        onDispose {
            Log.d("onDispose", "onDispose")
//            player = null
        }
    }
    LaunchedEffect(key1 = isPlaying) {
        cursor = 0
        while (isPlaying && cursor < voiceLength) {
            cursor += 100
            if (cursor >= voiceLength) {
                isPlaying = false
            }
            sliderPosition = cursor.toFloat() / voiceLength.toFloat()
            delay(100)
        }
    }

    Text(text = "${formatMillisecondToSecond(cursor)}/${formatMillisecondToSecond(voiceLength)}")
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            if (!isPlaying) {
                VoicePlayer.nullInstance()
                VoicePlayer.getInstance().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    setDataSource(S3_CONSTANT_URI + audioUrl)
                    setOnPreparedListener {
                        isReady = true
                        voiceLength = it.duration
                        it.start()
                    }
                    prepareAsync()
                    setOnCompletionListener {
                        if (voiceLength != 0) {
                            it.stop()
                            it.release()
                        }
                        isReady = true
                    }
                }
                isReady = false
            } else {
                VoicePlayer.getInstance().stop()
                VoicePlayer.getInstance().release()
            }
            isPlaying = !isPlaying
        }, enabled = isReady) {
            if (!isPlaying) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    tint = MaterialTheme.colors.secondary,
                    contentDescription = "introduce play button",
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Stop,
                    tint = MaterialTheme.colors.secondary,
                    contentDescription = "introduce stop button",
                )
            }

        }
        LinearProgressIndicator(sliderPosition)
    }
}

fun formatMillisecondToSecond(ms: Int): String {
    val second = (ms.toFloat() / 1000f).toInt()
    var secondString = ""
    if (second < 10) {
        secondString += "0"
    }
    return "00:${secondString + second}"
}
