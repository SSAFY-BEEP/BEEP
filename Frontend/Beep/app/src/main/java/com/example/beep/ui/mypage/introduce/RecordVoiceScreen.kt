package com.example.beep.ui.mypage.introduce

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beep.util.VoicePlayer
import com.example.beep.util.VoiceRecorder
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.home.formatSecond
import com.example.beep.ui.theme.BLUE500
import com.example.beep.ui.theme.PINK500
import com.google.accompanist.permissions.isGranted
import java.io.File
import java.io.IOException

enum class RecordState {
    BEFORE_RECORDING,
    ON_RECORDING,
    AFTER_RECORDING,
    ON_PLAYING,
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
    val filepath = context.cacheDir.absolutePath + "/temp.3gp"
    var file = File(filepath)

    LaunchedEffect(key1 = viewModel.currentState) {
        Log.d("CurrentState", viewModel.currentState.name)
    }

    DisposableEffect(key1 = Unit) {
        Log.d("DisposableEffect", "Disposable Effect Called!!")


        onDispose {
            if (viewModel.currentState == RecordState.ON_RECORDING) {
                VoiceRecorder.getInstanceWithoutContext()?.stop()
            }
            VoiceRecorder.nullInstance()
            if (viewModel.currentState == RecordState.ON_PLAYING) {
                VoicePlayer.getInstance().stop()
            }
            VoicePlayer.nullInstance()
            viewModel.currentState = RecordState.BEFORE_RECORDING
            viewModel.fileLength = 30;
            Log.d("DisposableEffect", "onDispose Called!!")
            if (file.exists())
                file.delete()
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp).padding(30.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlayButton(state = viewModel.currentState) {
                when (viewModel.currentState) {
                    RecordState.ON_PLAYING -> {
                        viewModel.stopTimer()
                        viewModel.currentState = RecordState.AFTER_RECORDING
                    }
                    else -> {
                        VoicePlayer.getInstance().apply {
                            try {
                                setDataSource(filepath)
                                setOnPreparedListener {
                                    Log.d("FileLength", file.readBytes().size.toString())
                                    Log.d("FileLength", duration.toString())
                                    viewModel.fileLength = duration / 1000 + 1
                                    start()
                                }
                                setOnCompletionListener {
                                    VoicePlayer.nullInstance()
                                    viewModel.stopTimer()
                                    viewModel.currentState = RecordState.AFTER_RECORDING
                                }
                                prepare()
                            } catch (e: IOException) {
                                Log.e("VoicePlayer", "prepare() failed")
                            }
                        }
                        viewModel.startTimer()
                        viewModel.currentState = RecordState.ON_PLAYING
                    }
                }
            }
            Text(
                text = "${formatSecond(viewModel.time)} / ${formatSecond(viewModel.fileLength)}",
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            RecordButton(
                state = viewModel.currentState
            ) {
                when (viewModel.currentState) {
                    RecordState.ON_RECORDING -> {
                        // 녹음 종료
                        VoiceRecorder.getInstanceWithoutContext()?.stop()
                        VoiceRecorder.nullInstance()
                        viewModel.stopTimer()
                        viewModel.currentState = RecordState.AFTER_RECORDING
                    }
                    else -> {
                        if (viewModel.currentState == RecordState.AFTER_RECORDING) {
                            viewModel.fileLength = 30
                        }
                        if (!voicePermissionState.status.isGranted) {
                            voicePermissionState.launchPermissionRequest()
                        }
                        if (voicePermissionState.status.isGranted) {
                            if (file.exists()) {
                                file.delete()
                            }
                            file = File(filepath)
                            VoiceRecorder.getInstance(context).apply {
                                setAudioSource(MediaRecorder.AudioSource.MIC)
                                setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
                                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                                setOutputFile(filepath)
                                try {
                                    prepare()
                                } catch (e: IOException) {
                                    Log.e("VoiceRecorder", "prepare() failed")
                                }
                                start()
                            }
                            viewModel.startTimer()
                            viewModel.currentState = RecordState.ON_RECORDING
                        }
                    }
                }
            }

        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = Color.White)
                    .height(40.dp)
                    .width(150.dp)
                    .border(width = 1.dp, color = BLUE500, shape = RoundedCornerShape(5.dp))
                    .clickable {
                        togglePopup()
                    },
            ) {
                Text(
                    text = "취소",
                    color = BLUE500,
                    fontSize = 15.sp
                )
            }
            Button(
                enabled = viewModel.currentState == RecordState.AFTER_RECORDING,
                onClick = {
                    viewModel.postIntroduce(
                        filepath = filepath,
                        togglePopup = { togglePopup() }
                    )
                },
                shape = RoundedCornerShape(5.dp),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier
                    .height(40.dp)
                    .width(150.dp),
                elevation = ButtonDefaults.elevation(0.dp),
            ) {
                Text(
                    text = "등록",
                    color =
                    if (
                        viewModel.currentState == RecordState.AFTER_RECORDING
                        || viewModel.currentState == RecordState.ON_PLAYING
                    ) {
                        Color.White
                    } else {
                        BLUE500
                    },
                    fontSize = 16.sp
                )
            }
        }

    }

}





@Composable
fun RecordButton(state: RecordState, action: () -> Unit) {
    Button(
        onClick = action,
        modifier = Modifier.size(100.dp),
        shape = RoundedCornerShape(100.dp),
        enabled = (state != RecordState.ON_PLAYING)
    ) {
        val color = PINK500
        when (state) {
            RecordState.ON_RECORDING -> {
                Icon(modifier = Modifier.size(72.dp),
                    imageVector = Icons.Filled.Stop,
                    tint = color,
                    contentDescription = "stop recording introduce"
                )
            }
            else -> {
                Icon(modifier = Modifier.size(72.dp),
                    imageVector = Icons.Filled.FiberManualRecord,
                    tint = color,
                    contentDescription = "record introduce",
                )
            }
        }

    }
}

@Composable
fun PlayButton(state: RecordState, action: () -> Unit) {
    IconButton(
        onClick = action,
        modifier = Modifier,
        enabled = (state == RecordState.AFTER_RECORDING || state == RecordState.ON_PLAYING)
    ) {
        val color = if (state == RecordState.ON_PLAYING) PINK500 else Color.Black
        when (state) {
            RecordState.ON_PLAYING -> {
                Icon(
                    imageVector = Icons.Filled.Stop,
                    tint = color,
                    contentDescription = "stop recorded introduce"
                )
            }
            else -> {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    tint = color,
                    contentDescription = "play recorded introduce",
                )
            }
        }
    }
}