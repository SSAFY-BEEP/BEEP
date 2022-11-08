package com.example.beep.ui.mypage

import android.app.Notification.Action
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.beep.ui.message.RecordVoiceScreen
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beep.util.collectAsStateLifecycleAware

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun IntroduceScreen(viewModel: IntroduceViewModel = viewModel()) {
    var isPopupVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val introduceUrl by viewModel.introduceUri.collectAsStateLifecycleAware(initial = "")

    LaunchedEffect(key1 = null) {
        println("LaunchedEffect Launched!!")
        viewModel.actionSender.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier) {
            Text(text = "GreetingSettingScreen")
            Button(onClick = {isPopupVisible = !isPopupVisible}) {
                Text(text = "자기소개 녹음")
            }
            Text(text = introduceUrl)
        }
        if (isPopupVisible) {
            RecordVoiceScreen(modifier = Modifier.offset(50.dp, 100.dp))
        }

        
    }
    
}