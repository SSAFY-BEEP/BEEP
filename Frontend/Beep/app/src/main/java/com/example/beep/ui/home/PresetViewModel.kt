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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PresetViewModel @Inject constructor(private val presetUseCase : PresetUseCase) :
    ViewModel() {

    var messagePreset: UiState<Array<String?>> by mutableStateOf(UiState.Loading)
    var contactPreset: UiState<Array<String?>> by mutableStateOf(UiState.Loading)

    val gson = Gson()

    //유저의 프리셋 리스트 가져오기(토큰 / 1=메세지, 2=연락처)
    suspend fun getPresetByToken(part : Int) {
        if(part == 1) messagePreset = UiState.Loading
        else contactPreset = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            presetUseCase.getUserPresetByToken(part).collectLatest {
                when(it){
                    is ResultType.Success -> {
                        var arr  = arrayOfNulls<String>(10)
                        var list : List<PresetResponse> = gson.fromJson<List<PresetResponse>>(gson.toJson(it.data.data))

                        for(a in list){
                            arr[a.number] = a.content
                        }

                        if(part==1){
                            messagePreset = UiState.Success(arr)
                        } else{
                            contactPreset = UiState.Success(arr)
                        }

                        Log.d("getPreset"+part, it.toString())
                    } else -> {
                        Log.d("getPreset"+part, "Error!")
                        UiState.Error
                    }
                }
            }
        }
    }


    //프리셋 수정/추가
    fun updatePreset(number : Int, part : Int, content : String) {
        viewModelScope.launch(Dispatchers.IO) {
            presetUseCase.updatePreset( number, part, content).collectLatest {
                when(it){
                    is ResultType.Success -> {
                        Log.d("updatePreset", it.toString())
                        getPresetByToken(part)
                    } else ->{
                        Log.d("updatePreset", "Error!")
                    }
                }

            }
        }
    }

    //프리셋 삭제
    fun deletePreset(pid : Long, part : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            presetUseCase.deletePreset(pid).collectLatest {
                Log.d("deletePreset", it.toString())
                getPresetByToken(part)
            }
        }
    }

//    init{
//        getPresetByToken(1)
//        getPresetByToken(2)
//    }
}
