package com.example.beep.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.mypage.PresetResponse
import com.example.beep.domain.PresetUseCase
import com.example.beep.ui.mypage.introduce.UiState
import com.example.beep.util.ResultType
import com.example.beep.util.fromJson
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeyboardViewModel @Inject constructor(private val presetUseCase : PresetUseCase) :
    ViewModel() {

    var state by mutableStateOf(KeyboardState())





    fun onAction(action: KeyboardAction) {
        when(action) {
            is KeyboardAction.Number -> enterNumber(action.number)
            is KeyboardAction.Clear -> state = KeyboardState()
            is KeyboardAction.Change -> enterChange(action.number)
            is KeyboardAction.Delete -> delete()
        }
    }


    private fun delete() {
        if(state.number1.isNotBlank()) {
            state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }

    private fun enterChange(number: String) {
        state = state.copy(
            number1 = state.number1.dropLast(1) + number
        )
        return
    }

    private fun enterNumber(number: String) {

    if(state.number1.length >= MAX_NUM_LENGTH) {
        return
    }
    state = state.copy(
        number1 = state.number1 + number
    )
        while (state.number1.length > MAX_NUM_LENGTH) {
            state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    return
    }


    var contactPreset: UiState<Array<String?>> by mutableStateOf(UiState.Loading)
    var messagePreset: UiState<Array<String?>> by mutableStateOf(UiState.Loading)

    val gson = Gson()

    //유저의 프리셋 리스트 가져오기(토큰 / 1=메세지, 2=연락처)
    fun getMessage(number: String) {
        viewModelScope.launch(Dispatchers.IO) {
            presetUseCase.getUserPresetByToken(1).collectLatest {
                when(it){
                    is ResultType.Success -> {
                        var arr  = arrayOfNulls<String>(10)
                        var list : List<PresetResponse> = gson.fromJson<List<PresetResponse>>(gson.toJson(it.data.data))

                        for(a in list){
                            arr[a.number] = a.content
                        }
                        Log.d("viewModel", "Hello")
                        messagePreset = UiState.Success(arr)
                        Log.d("getPreset"+1, it.toString())
                    } else -> {
                    Log.d("getPreset"+1, "Error!")
                    UiState.Error
                }
                }
            }
        }
    }


    companion object {
        private const val MAX_NUM_LENGTH = 11
    }

}