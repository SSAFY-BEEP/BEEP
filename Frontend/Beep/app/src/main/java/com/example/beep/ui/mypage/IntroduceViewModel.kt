package com.example.beep.ui.mypage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.mypage.S3Request
import com.example.beep.domain.S3UseCase
import com.example.beep.ui.message.UiState
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
            if (result.status == "OK") {
                introduceVoiceUiState = UiState.Success(result.data?:"")
            } else {
                introduceVoiceUiState = UiState.Error
            }
        }
    }

    fun postIntroduce(voice: MultipartBody.Part) {
        viewModelScope.launch {
            s3UseCase.postIntroduceUseCase(voice)
        }
    }

    fun deleteIntroduce(introduceUri: String) {
        viewModelScope.launch {
            s3UseCase.deleteIntroduceUseCase(S3Request(introduceUri))
        }
    }

    init {
        getIntroduce()
    }
}