package com.example.beep.ui.home

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
import com.example.beep.util.VoicePlayer
import com.example.beep.util.VoiceRecorder

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun BbibbiDoRecord(
    toSendMsg: () -> Unit,
    toAskRecord: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val currentState = homeViewModel.recordScreenState
    val context = LocalContext.current
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
//            .background(Color.Cyan)
            .width(320.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DoRecordScreen(currentState = currentState)
        Spacer(modifier = modifier.height(35.dp))
        Row(modifier = Modifier.height(60.dp), verticalAlignment = Alignment.Bottom) {
            CancelBtn(onClick = {
                if (currentState.cancelFunc != null)
                    homeViewModel.onAction(currentState.cancelFunc)
                else
                    toAskRecord()
            })
            LeftBtn(onClick = {})
            RightBtn(onClick = {})
            ConfirmBtn(onClick = {
                if (currentState.confirmFunc != null)
                    homeViewModel.onAction(currentState.confirmFunc)
                else
                    toSendMsg()
            })
        }
    }
}

@Composable
fun ConfirmBtn(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.height(67.dp),
        shape = RoundedCornerShape(65.dp, 20.dp, 50.dp, 0.dp),
        onClick = onClick
    ) {
    }
}

@Composable
fun RightBtn(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(onClick = onClick) {

    }
}

@Composable
fun LeftBtn(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(onClick = onClick) {

    }
}

@Composable
fun CancelBtn(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(onClick = onClick) {

    }
}

@Composable
fun DoRecordScreen(modifier: Modifier = Modifier, currentState: RecordScreenUiState) {
    Text(text = currentState.textForScreen)
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
