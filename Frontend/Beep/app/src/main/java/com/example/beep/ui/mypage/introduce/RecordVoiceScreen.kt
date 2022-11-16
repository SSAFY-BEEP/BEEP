package com.example.beep.ui.mypage.introduce

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.beep.util.VoicePlayer
import com.example.beep.util.VoiceRecorder
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.home.formatSecond
import com.google.accompanist.permissions.isGranted
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


    DisposableEffect(key1 = Unit) {
        Log.d("DisposableEffect", "Disposable Effect Called!!")
        VoiceRecorder.nullInstance()
        VoiceRecorder.getInstance(context)
        VoicePlayer.nullInstance()
        VoicePlayer.getInstance()
        File(filepath)

        onDispose {
            Log.d("DisposableEffect", "onDispose Called!!")
            VoiceRecorder.nullInstance()
            VoicePlayer.nullInstance()
            val file = File(filepath)
            if (file.exists())
                file.delete()
        }
    }

    Column(
        modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${formatSecond(viewModel.time)}:${formatSecond(viewModel.fileLength)}")

        RecordButton(state = viewModel.currentState) {
            when (viewModel.currentState) {
                RecordState.BEFORE_RECORDING -> {
                    if (!voicePermissionState.status.isGranted) {
                        voicePermissionState.launchPermissionRequest()
                    }
                    if (voicePermissionState.status.isGranted) {
                        viewModel.fileLength = 30
                        startRecording(context, filepath)
                        viewModel.startTimer()
                        viewModel.currentState = RecordState.ON_RECORDING
                    }
                }
                RecordState.ON_RECORDING -> {
                    stopRecordingIntroduce(filepath,
                        onComplete = {
                            stopPlaying()
                            viewModel.stopTimer()
                            viewModel.currentState = RecordState.ASK_POST
                        },
                        onPrepared = { duration: Int ->
                            viewModel.fileLength = duration
                        })
                    viewModel.stopTimer()
                    var files = context.cacheDir.listFiles()
                    for (file in files) {
                        Log.d("Files", file.absolutePath)
                    }
                    viewModel.currentState = RecordState.AFTER_RECORDING
                }
                RecordState.AFTER_RECORDING -> {
                    VoicePlayer.getInstance().start()
                    viewModel.startTimer()
                    viewModel.currentState = RecordState.ON_PLAYING
                }
                RecordState.ON_PLAYING -> {
                    stopPlaying()
                    viewModel.stopTimer()
                    viewModel.currentState = RecordState.ASK_POST
                }
                RecordState.ASK_POST -> {
                    viewModel.fileLength = 30
                    VoiceRecorder.nullInstance()
                    VoicePlayer.nullInstance()
                    viewModel.currentState = RecordState.BEFORE_RECORDING
                }
            }
        }
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                enabled = viewModel.currentState == RecordState.AFTER_RECORDING
                        || viewModel.currentState == RecordState.ON_PLAYING
                        || viewModel.currentState == RecordState.ASK_POST,
                onClick = {
                    viewModel.postIntroduce(
                        filepath = filepath,
                        togglePopup = {togglePopup()}
                    )
                }) {
                Text(text = "바꾸기")
            }
            Button(onClick = {
                when (viewModel.currentState) {
                    RecordState.ON_RECORDING -> {
                        stopRecording()
                    }
                    RecordState.ON_PLAYING -> {
                        stopPlaying()
                    }
                }
                togglePopup()
            }) {
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
fun stopRecording() {
    VoiceRecorder.getInstanceWithoutContext()?.run {
        stop()
        release()
    }
    VoiceRecorder.nullInstance()
}

@RequiresApi(Build.VERSION_CODES.S)
fun stopRecordingIntroduce(
    filepath: String,
    onComplete: () -> Unit,
    onPrepared: (Int) -> Unit
) {
    VoiceRecorder.getInstanceWithoutContext()?.run {
        stop()
        release()
    }
    VoiceRecorder.nullInstance()
    VoicePlayer.nullInstance()
    VoicePlayer.getInstance().apply {
        setDataSource(filepath)
        setOnPreparedListener {
            onPrepared(duration / 1000)
        }
        setOnCompletionListener {
            onComplete()
        }
        prepare()
    }
}

fun startPlaying(filepath: String, onComplete: () -> Unit, onPrepared: (Int) -> Unit) {
    VoicePlayer.nullInstance()
    VoicePlayer.getInstance().apply {
        setDataSource(filepath)
        setOnPreparedListener {
            onPrepared(duration / 1000)
        }
        setOnCompletionListener {
            onComplete()
        }
        prepare()
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