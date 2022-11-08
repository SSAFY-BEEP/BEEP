package com.example.beep.ui.mypage

import android.app.Notification
import android.app.Notification.Action
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.domain.S3UseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

class IntroduceViewModel @Inject constructor(private val s3UseCase: S3UseCase): ViewModel() {
    val _actionSender = Channel<String>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val actionSender = _actionSender.receiveAsFlow()
    suspend fun produceResult(result: String) {
        _actionSender.send(result)
    }

    val currentIntroduceUrl: Flow<String> = s3UseCase.getIntroduceUseCase()

    fun postIntroduce(voice: MultipartBody.Part) {
        viewModelScope.launch {
            s3UseCase.postIntroduceUseCase(voice).collectLatest {

            }
        }
    }
}