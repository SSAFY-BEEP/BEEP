package com.example.beep.ui.mypage.introduce

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.mypage.S3Request
import com.example.beep.domain.S3UseCase
import com.example.beep.util.ResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class IntroduceViewModel @Inject constructor(private val s3UseCase: S3UseCase): ViewModel() {
    var introduceVoiceUiState: UiState<String> by mutableStateOf(UiState.Loading)


    var introduceUrl by mutableStateOf("")

    fun getIntroduce() {
        viewModelScope.launch {
            introduceVoiceUiState = UiState.Loading
            val result = s3UseCase.getIntroduceUseCase()
            introduceVoiceUiState = when (result) {
                is ResultType.Success -> {
                    introduceUrl = result.data ?: ""
                    UiState.Success("")
                }
                else -> {
                    UiState.Error
                }
            }
        }
    }

    fun postIntroduce(voice: MultipartBody.Part) {
        viewModelScope.launch {
            introduceVoiceUiState = UiState.Loading
            val result = s3UseCase.postIntroduceUseCase(voice)
            introduceVoiceUiState = when (result) {
                is ResultType.Success -> {
                    UiState.Success("")
                }
                else -> {
                    UiState.Error
                }
            }
        }
    }

    fun deleteIntroduce(introduceUri: String) {
        viewModelScope.launch {
            val result = s3UseCase.deleteIntroduceUseCase(S3Request(introduceUri))
            introduceVoiceUiState = when (result) {
                is ResultType.Success -> {
                    UiState.Success("")
                }
                else -> {
                    UiState.Error
                }
            }
        }
    }

    init {
        getIntroduce()
    }
}