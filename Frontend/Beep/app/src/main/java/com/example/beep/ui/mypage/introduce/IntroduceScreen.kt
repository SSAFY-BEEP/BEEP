package com.example.beep.ui.mypage.introduce

import android.media.AudioAttributes
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.beep.R
import com.example.beep.ui.base.ErrorScreen
import com.example.beep.ui.base.LoadingScreen
import com.example.beep.ui.home.galmurinineFont
import com.example.beep.ui.theme.BLUE500
import com.example.beep.util.S3_CONSTANT_URI
import com.example.beep.util.VoicePlayer
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun IntroduceScreen(
    navController: NavController,
    viewModel: IntroduceViewModel = viewModel()) {
    var isRecordScreen by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isRecordScreen) {
        viewModel.getIntroduce()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 0.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
            ) {
                Icon(
                    modifier = Modifier.size(17.dp),
                    painter = painterResource(R.drawable.backbutton_gray),
                    contentDescription = "뒤로가기"
                )
            }

            Text(
                modifier = Modifier
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
                textAlign = TextAlign.Center,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                text = "인사말 설정"
            )
        }
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
    var toggleDeletePopup by remember { mutableStateOf(false) }

    if (audioUrl == "") {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "등록된 인사말이 없습니다.")
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .clickable { toggleScreen() }
                    .height(40.dp)
                    .width(100.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(BLUE500)
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "등록하기",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
//            Button(onClick = toggleScreen) {
//                Text(text = "등록")
//            }
        }

    } else {
        DisposableEffect(key1 = Unit) {
            VoicePlayer.getInstance()
            try {
                if (viewModel.introduceUrl != null || viewModel.introduceUrl != "") {
                    VoicePlayer.getInstance().apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
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
            } catch (e: Exception) {
                viewModel.introduceVoiceUiState = UiState.Error
            }

            onDispose {
                VoicePlayer.nullInstance()
            }
        }
        LaunchedEffect(key1 = isPlaying) {
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
            Spacer(modifier = Modifier.height(10.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${
                        formatMillisecondToSecond(
                            cursor
                        )
                    } / ${
                        formatMillisecondToSecond(
                            voiceLength
                        )
                    }"
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(20.dp, 0.dp)
                ) {
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
                    LinearProgressIndicator(
                        sliderPosition,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 20.dp, 0.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { toggleDeletePopup = !toggleDeletePopup },
                    colors = ButtonDefaults.buttonColors(BLUE500),
                    modifier = Modifier
                        .width(130.dp)
                        .padding(20.dp, 0.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "인사말 삭제",
                        color = Color.White
                    )
                }
                Button(
                    onClick = toggleScreen,
                    colors = ButtonDefaults.buttonColors(BLUE500),
                    modifier = Modifier
                        .width(130.dp)
                        .padding(20.dp, 0.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "인사말 등록",
                        color = Color.White,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
    if (toggleDeletePopup)
        AlertDialog(onDismissRequest = { toggleDeletePopup = !toggleDeletePopup }, title = {
            Text(text = "삭제하시겠습니까?")
        },
            text = {
                Text(text = "인사말이 삭제됩니다.")
            }, confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteIntroduce()
                    toggleDeletePopup = !toggleDeletePopup
                }) {
                    Text("확인")
                }
            }, dismissButton = {
                TextButton(onClick = { toggleDeletePopup = !toggleDeletePopup }) {
                    Text("취소")
                }
            })
}

fun formatMillisecondToSecond(ms: Int): String {
    val second = (ms.toFloat() / 1000f).toInt()
    var secondString = ""
    if (second < 10) {
        secondString += "0"
    }
    return "00:${secondString + second}"
}
