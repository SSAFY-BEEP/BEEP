package com.example.beep.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.domain.PresetUseCase
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class PresetViewModel @Inject constructor(private val presetUseCase : PresetUseCase) :
    ViewModel() {

    //유저의 프리셋 리스트 가져오기
    fun getPreset() {
        viewModelScope.launch(Dispatchers.IO) {
            presetUseCase.getUserPreset().collectLatest {
                Log.d("getPreset", it.toString())
            }
        }
    }

    fun getPresetByToken() {
        viewModelScope.launch(Dispatchers.IO) {
            presetUseCase.getUserPresetByToken().collectLatest {
                Log.d("getUserPresetByToken", it.toString())
            }
        }
    }

    //프리셋 수정/추가
    fun updatePreset(number : Int, part : Int, content : String) {
        viewModelScope.launch(Dispatchers.IO) {
            presetUseCase.updatePreset( number, part, content).collectLatest {
                Log.d("updatePreset", it.toString())
            }
        }
    }

    //프리셋 삭제
    fun deletePreset(pid : Long) {
        viewModelScope.launch(Dispatchers.IO) {
            presetUseCase.deletePreset(pid).collectLatest {
                Log.d("deletePreset", it.toString())
            }
        }
    }
}
