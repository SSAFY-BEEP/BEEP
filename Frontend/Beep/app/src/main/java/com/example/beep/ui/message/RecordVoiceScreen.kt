package com.example.beep.ui.message

import android.content.Context
import android.media.MediaRecorder
import android.media.audiofx.Visualizer
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.beep.util.VoicePlayer
import com.example.beep.util.VoiceRecorder
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File

enum class RecordState {
    BEFORE_RECORDING,
    ON_RECORDING,
    AFTER_RECORDING,
    ON_PLAYING,
}

@RequiresApi(Build.VERSION_CODES.S)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RecordVoiceScreen(modifier: Modifier = Modifier) {
    val voicePermissionState = rememberPermissionState(
        android.Manifest.permission.RECORD_AUDIO
    )
    val fileName: String = "temp.3gp"
    var currentState by remember { mutableStateOf(RecordState.BEFORE_RECORDING) }
    val context = LocalContext.current

    Column(modifier = modifier) {
        Text(text = "Record voice Screen")

        RecordButton(state = currentState) {
            when (currentState) {
                RecordState.BEFORE_RECORDING -> {
                    if (!voicePermissionState.hasPermission) {
                        voicePermissionState.launchPermissionRequest()
                    }
                    if (voicePermissionState.hasPermission) {
                        startRecording(context, fileName)
                        currentState = RecordState.ON_RECORDING
                    }
                }
                RecordState.ON_RECORDING -> {
                    stopRecording(context)
                    var files = context.fileList()
                    for (file in files) {
                        Log.d("Files", file)
                    }
                    currentState = RecordState.AFTER_RECORDING
                }
                RecordState.AFTER_RECORDING -> {
                    currentState = RecordState.ON_PLAYING
                }
                RecordState.ON_PLAYING -> {
                    currentState = RecordState.BEFORE_RECORDING
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
fun startRecording(context: Context, fileName: String) {
    VoiceRecorder.getInstance(context).apply {
        setAudioSource(MediaRecorder.AudioSource.MIC)
        setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

        setOutputFile(
            File(
                context.filesDir,
                fileName
            )
        )
        prepare()
    }.start()
}

@RequiresApi(Build.VERSION_CODES.S)
fun stopRecording(context: Context) {
    VoiceRecorder.getInstance(context).run {
        stop()
        release()
    }
}

fun startPlaying(fileName: String) {
    VoicePlayer.getInstance().apply {
        setDataSource(fileName)
        prepare()
        setOnCompletionListener { stopPlaying() }
    }
}

fun stopPlaying() {
    VoicePlayer.getInstance().release()
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
    }
    Button(onClick = action) {
        Text(text = text)
    }
}