package com.example.beep.ui.mypage

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Build
import android.speech.tts.Voice
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.beep.ui.message.RecordVoiceScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.ui.message.stopPlaying
import com.example.beep.util.S3_CONSTANT_URI
import com.example.beep.util.VoicePlayer
import com.example.beep.util.collectAsStateLifecycleAware
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

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
fun IntroducePlayer(audioUrl: String) {
    var voiceLength by remember { mutableStateOf(0) }
    var isReady by remember { mutableStateOf(false) }
    var player = MediaPlayer()
    player.apply {
        setAudioAttributes(
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        )
        setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
        setOnPreparedListener {
            isReady = true
            voiceLength = player.duration
        }
        prepareAsync()
        setOnCompletionListener { if (voiceLength != 0) player.stop() }
    }
    Log.d("Datasource", S3_CONSTANT_URI + audioUrl)
    var isPlaying by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = isPlaying) {
        withContext(Dispatchers.Default) {
            while (isPlaying) {
                isPlaying = player.isPlaying
                val p = player.isPlaying
                Log.d("PlayTick", p.toString())
                sliderPosition = player.currentPosition / voiceLength.toFloat()
                delay(500)
            }
        }
    }
    Text(text = "${sliderPosition}/$voiceLength")
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            if (!isPlaying) {
                player.start()
            } else {
                player.stop()
                player.release()
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
    }
}
