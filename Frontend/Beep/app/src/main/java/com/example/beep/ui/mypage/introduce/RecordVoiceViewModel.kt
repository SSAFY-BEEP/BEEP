package com.example.beep.ui.mypage.introduce

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.domain.S3UseCase
import com.example.beep.util.ResultType
import com.example.beep.util.VoicePlayer
import com.example.beep.util.VoiceRecorder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import java.util.*
import javax.inject.Inject

sealed interface UiState<out T : Any> {
    data class Success<out T : Any>(val data: T) : UiState<T>
    object Error : UiState<Nothing>
    object Loading : UiState<Nothing>
}

@HiltViewModel
class RecordVoiceViewModel @Inject constructor(private val s3UseCase: S3UseCase) : ViewModel() {
    var recordVoiceUiState: UiState<String> by mutableStateOf(UiState.Success("Initial State"))
    var currentState by mutableStateOf(RecordState.BEFORE_RECORDING)
    var timerTask: Timer? = null
    var time by mutableStateOf(0)
    var isRunning by mutableStateOf(false)
    var fileLength by mutableStateOf(30)

    @RequiresApi(Build.VERSION_CODES.S)
    fun startTimer() {
        isRunning = true
        timerTask = kotlin.concurrent.timer(period = 1000) {
            time++
            if (time >= fileLength) {
                stopTimer()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun stopTimer() {
        timerTask?.cancel()

        time = 0
        isRunning = false
        if (currentState == RecordState.ON_RECORDING) {
            VoiceRecorder.getInstanceWithoutContext()?.run {
                stop()
                release()
                currentState = RecordState.AFTER_RECORDING
            }
        } else if (currentState == RecordState.ON_PLAYING) {
            VoicePlayer.getInstance().run {
                stop()
                release()
                currentState = RecordState.ASK_POST
            }
        }
    }

    fun postIntroduce(filepath: String, togglePopup: () -> Unit) {
        viewModelScope.launch {
            recordVoiceUiState = UiState.Loading
            val file = File(filepath)
            val fis = FileInputStream(file)
            val byteArray = fis.readBytes()
            val partFile = MultipartBody.Part.createFormData(
                "voice",
                "${System.currentTimeMillis()}record.mp3",
                byteArray.toRequestBody(contentType = "multipart/form-data".toMediaTypeOrNull())
            )
            val result = s3UseCase.postIntroduceUseCase(partFile)
            recordVoiceUiState = when (result) {
                is ResultType.Success -> {
                    UiState.Success(result.data)
                }
                else -> {
                    UiState.Error
                }
            }
        }
    }
}