package com.example.beep.ui.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.domain.MessageUseCase
import com.example.beep.domain.S3UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import javax.inject.Inject

@HiltViewModel
class RecordVoiceViewModel @Inject constructor(private val s3UseCase: S3UseCase): ViewModel() {
    val _actionSender = Channel<String>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val actionSender = _actionSender.receiveAsFlow()

    private suspend fun produceResult(result: String) {
        _actionSender.send(result)
    }

    fun postIntroduce(filepath : String) {
        viewModelScope.launch {
            val file = File(filepath)
            val fis = FileInputStream(file)
            val byteArray =fis.readBytes()
            val partFile = MultipartBody.Part.createFormData(
                "voice",
                "${System.currentTimeMillis()}record.mp3",
                byteArray.toRequestBody(contentType = "multipart/form-data".toMediaTypeOrNull())
            )
            s3UseCase.postIntroduceUseCase(partFile).collectLatest {
                produceResult(it)
            }
            file.delete()
        }
    }
}