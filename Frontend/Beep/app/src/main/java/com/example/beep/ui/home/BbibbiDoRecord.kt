package com.example.beep.ui.home

import android.Manifest
import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.ui.message.stopPlaying
import com.example.beep.util.VoicePlayer
import com.example.beep.util.VoiceRecorder
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BbibbiDoRecord(
    toSendMsg: () -> Unit,
    toAskRecord: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val voicePermissionState = rememberPermissionState(
        Manifest.permission.RECORD_AUDIO
    )
    var filepath = context.cacheDir.absolutePath + "/temp.3gp"
    val currentState = homeViewModel.recordScreenState

    DisposableEffect(key1 = Unit) {
        Log.d("DisposableEffect", "Disposable Effect Called!!")
        VoiceRecorder.nullInstance()
        VoiceRecorder.getInstance(context)
        VoicePlayer.nullInstance()
        VoicePlayer.getInstance()

        onDispose {
            Log.d("DisposableEffect", "onDispose Called!!")
            VoiceRecorder.nullInstance()
            VoicePlayer.nullInstance()
        }
    }
    Column(
        modifier = modifier
            .width(320.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DoRecordScreen(currentState = currentState)
        Spacer(modifier = modifier.height(35.dp))
        Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.Bottom) {
            CancelBtn(currentState = currentState)
            LeftBtn(currentState = currentState)
            RightBtn(currentState = currentState)
            ConfirmBtn(currentState = currentState)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun ConfirmBtn(
    modifier: Modifier = Modifier,
    currentState: RecordMessageState
) {
    when (currentState) {
        RecordMessageState.Before -> {}
    }
    Button(
        modifier = modifier.height(67.dp),
        shape = RoundedCornerShape(65.dp, 20.dp, 50.dp, 0.dp),
        onClick = event
    ) {
    }
}

@Composable
fun RightBtn(
    modifier: Modifier = Modifier, currentState: RecordMessageState
) {
    Button(onClick = onClick) {

    }
}

@Composable
fun LeftBtn(
    modifier: Modifier = Modifier, currentState: RecordMessageState
) {
    Button(onClick = onClick) {

    }
}

@Composable
fun CancelBtn(
    modifier: Modifier = Modifier, currentState: RecordMessageState
) {
    Button(onClick = onClick) {

    }
}

@Composable
fun DoRecordScreen(
    modifier: Modifier = Modifier, currentState: RecordMessageState
) {
//    when (currentState) {
//        RecordScreenState.Before -> {
//            Text(text = "● : 녹음 시작 | / : 취소", fontSize = 19.sp)
//        }
//        RecordScreenState.Recording -> {
//            Text(text = "녹음중 $recordingTime/00:30", fontSize = 19.sp)
//        }
//        RecordScreenState.Finished -> {
//            Text(text = "● : 재생 | / : 다시 녹음", fontSize = 19.sp)
//        }
//        RecordScreenState.Playing -> {
//
//        }
//    }
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