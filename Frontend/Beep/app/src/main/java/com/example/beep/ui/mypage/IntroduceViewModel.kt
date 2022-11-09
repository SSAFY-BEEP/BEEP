package com.example.beep.ui.mypage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.mypage.S3Request
import com.example.beep.domain.S3UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class IntroduceViewModel @Inject constructor(private val s3UseCase: S3UseCase): ViewModel() {
    val _actionSender = Channel<String>(onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val actionSender = _actionSender.receiveAsFlow()

    private suspend fun produceResult(result: String) {
        _actionSender.send(result)
    }

//    val introduceUriFlow: Flow<String> = s3UseCase.introduceFlow

    var introduceUrl by mutableStateOf("")

    fun getIntroduce() {
        viewModelScope.launch {
            introduceUrl = s3UseCase.getIntroduceUseCase()
        }
    }

    fun postIntroduce(voice: MultipartBody.Part) {
        viewModelScope.launch {
            s3UseCase.postIntroduceUseCase(voice).collectLatest {
                produceResult(it)
            }
        }
    }

    fun deleteIntroduce(introduceUri: String) {
        viewModelScope.launch {
            s3UseCase.deleteIntroduceUseCase(S3Request(introduceUri)).collectLatest {
                produceResult(it)
            }
        }
    }

    init {
        getIntroduce()
    }
}