package com.example.beep.ui.message

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.beep.util.VoicePlayer
import com.example.beep.util.VoiceRecorder
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.mypage.BeepForTest
import java.io.File

enum class RecordState {
    BEFORE_RECORDING,
    ON_RECORDING,
    AFTER_RECORDING,
    ON_PLAYING,
    ASK_POST
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RecordVoiceScreen(
    modifier: Modifier = Modifier,
    viewModel: RecordVoiceViewModel = viewModel(),
    togglePopup: () -> Unit
) {
    when (viewModel.recordVoiceUiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }
        is UiState.Success<String> -> {
            RecordSuccessScreen(togglePopup = togglePopup)
        }
        is UiState.Error -> {
            ErrorScreen()
        }
    }

}

@Composable
fun RecordErrorScreen() {
    Text(text = "에러가 뜨다니 ㅠㅠㅠ")
}

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RecordSuccessScreen(
    modifier: Modifier = Modifier,
    togglePopup: () -> Unit,
    viewModel: RecordVoiceViewModel = viewModel()
) {
    val context = LocalContext.current
    val voicePermissionState = rememberPermissionState(
        android.Manifest.permission.RECORD_AUDIO
    )
    var filepath = context.cacheDir.absolutePath + "/temp.3gp"
    var currentState by remember { mutableStateOf(RecordState.BEFORE_RECORDING) }
    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Record voice Screen")

        RecordButton(state = currentState) {
            when (currentState) {
                RecordState.BEFORE_RECORDING -> {
                    if (!voicePermissionState.hasPermission) {
                        voicePermissionState.launchPermissionRequest()
                    }
                    if (voicePermissionState.hasPermission) {
                        if (File(filepath).exists()) {
                            File(filepath).delete()
                        }
                        startRecording(context, filepath)
                        currentState = RecordState.ON_RECORDING
                    }
                }
                RecordState.ON_RECORDING -> {
                    stopRecording(context)
                    var files = context.cacheDir.listFiles()
                    for (file in files) {
                        Log.d("Files", file.absolutePath)
                    }
                    currentState = RecordState.AFTER_RECORDING
                }
                RecordState.AFTER_RECORDING -> {
                    startPlaying(
                        filepath,
                        changeCurrentState = { currentState = RecordState.ASK_POST })
                    currentState = RecordState.ON_PLAYING
                }
                RecordState.ON_PLAYING -> {
                    stopPlaying()
                    currentState = RecordState.ASK_POST
                }
                RecordState.ASK_POST -> {
                    currentState = RecordState.BEFORE_RECORDING
                }
            }
        }
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                enabled = currentState == RecordState.AFTER_RECORDING
                        || currentState == RecordState.ON_PLAYING
                        || currentState == RecordState.ASK_POST,
                onClick = {
                    viewModel.postIntroduce(
                        filepath = filepath,
                        togglePopup = togglePopup
                    )
                }) {
                Text(text = "바꾸기")
            }
            Button(onClick = togglePopup) {
                Text(text = "취소")
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.S)
fun startRecording(context: Context, filepath: String) {
    VoiceRecorder.nullInstance()
    VoiceRecorder.getInstance(context).apply {
        setAudioSource(MediaRecorder.AudioSource.MIC)
        setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        setOutputFile(filepath)
        prepare()
    }.start()
}

@RequiresApi(Build.VERSION_CODES.S)
fun stopRecording(context: Context) {
    VoiceRecorder.getInstance(context).run {
        stop()
        release()
    }
    VoiceRecorder.nullInstance()
}

fun startPlaying(filepath: String, changeCurrentState: () -> Unit) {
    VoicePlayer.nullInstance()
    VoicePlayer.getInstance().apply {
        setDataSource(filepath)
        prepare()
        setOnCompletionListener {
            stopPlaying()
            changeCurrentState()
        }
    }.start()
}

fun stopPlaying() {
    VoicePlayer.getInstance().release()
    VoicePlayer.nullInstance()
}

@Composable
fun RecordButton(state: RecordState, action: () -> Unit) {
    val text = when (state) {
        RecordState.BEFORE_RECORDING -> {
            "녹음하기"
        }
        RecordState.ON_RECORDING -> {
            "녹음중지"
        }
        RecordState.AFTER_RECORDING -> {
            "녹음재생"
        }
        RecordState.ON_PLAYING -> {
            "재생중지"
        }
        RecordState.ASK_POST -> {
            "다시 녹음하기"
        }
    }
    Button(onClick = action) {
        Text(text = text)
    }
}