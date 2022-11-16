package com.example.beep.ui.mypage.introduce

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.util.S3_CONSTANT_URI
import com.example.beep.util.VoicePlayer
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun IntroduceScreen(viewModel: IntroduceViewModel = viewModel()) {
    var isRecordScreen by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = isRecordScreen) {
        viewModel.getIntroduce()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "나의 인사말")
        if (isRecordScreen) {
            RecordVoiceScreen() {
                isRecordScreen = !isRecordScreen
            }
        } else {
            IntroducePlayer(viewModel.introduceUrl) {
                isRecordScreen = !isRecordScreen
            }
        }
    }
}

@Composable
fun IntroducePlayer(
    audioUrl: String,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: IntroduceViewModel = viewModel(),
    toggleScreen: () -> Unit
) {
    when (viewModel.introduceVoiceUiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }
        is UiState.Success<String> -> {
            IntroduceSuccessScreen(audioUrl, lifecycleOwner, toggleScreen)
        }
        is UiState.Error -> {
            ErrorScreen()
        }
    }
}

@Composable
fun IntroduceSuccessScreen(
    audioUrl: String,
    lifecycleOwner: LifecycleOwner,
    toggleScreen: () -> Unit,
    viewModel: IntroduceViewModel = viewModel(),
) {
    var voiceLength by remember { mutableStateOf(0) }
    var isReady by remember { mutableStateOf(false) }
    var isPlaying by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableStateOf(0f) }
    var cursor by remember { mutableStateOf(0) }

    if (audioUrl == "") {
        Text(text = "등록된 소개 메시지가 없습니다.")
        Button(onClick = toggleScreen) {
            Text(text = "자기소개 등록")
        }
    } else {
        Log.d("VoicePlayer", "VoicePlayer Initialized")

        DisposableEffect(key1 = Unit) {
            Log.d("DisposableEvent", "DisposableEvent")
            VoicePlayer.getInstance()
            if (viewModel.introduceUrl != null || viewModel.introduceUrl != "") {
                VoicePlayer.getInstance().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    setDataSource(S3_CONSTANT_URI + viewModel.introduceUrl)
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
                        isPlaying = false
                    }
                }
            }
            onDispose {
                Log.d("onDispose", "onDispose")
                VoicePlayer.nullInstance()
            }
        }
        LaunchedEffect(key1 = isPlaying) {
            Log.d("LaunchedEffect", "IntroducePlay")
            cursor = 0
//            voiceLength = VoicePlayer.getInstance().duration
            while (isPlaying && cursor < voiceLength) {
                cursor += 100
                if (cursor >= voiceLength) {
                    isPlaying = false
                }
                sliderPosition = cursor.toFloat() / voiceLength.toFloat()
                delay(100)
            }
            cursor = 0
            sliderPosition = 0f
        }
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
//            Text(text = viewModel.introduceUrl)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    if (!isPlaying) {
                        VoicePlayer.nullInstance()
                        VoicePlayer.getInstance().apply {
                            setAudioAttributes(
                                AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .build()
                            )
                            setDataSource(S3_CONSTANT_URI + audioUrl)
                            setOnPreparedListener {
                                voiceLength = it.duration
                                it.start()
                                isPlaying = true
                            }
                            prepareAsync()
                            setOnCompletionListener {
                                if (voiceLength != 0) {
                                    it.stop()
                                    it.release()
                                    isPlaying = false
//                                cursor = 0
//                                sliderPosition = 0f
                                }
                                isReady = false
                            }
                        }
                    } else {
                        VoicePlayer.getInstance().stop()
                        VoicePlayer.getInstance().release()
                        isPlaying = false
                        cursor = 0
                        sliderPosition = 0f
                    }
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
                Text(
                    text = "${formatMillisecondToSecond(cursor)}/${
                        formatMillisecondToSecond(
                            voiceLength
                        )
                    }"
                )
            }
            Button(onClick = toggleScreen) {
                Text(text = "자기소개 바꾸기")
            }
        }
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
