package com.example.beep.ui.mypage

import androidx.lifecycle.ViewModel
import com.example.beep.data.dto.mypage.PresetResponse
import com.example.beep.domain.PresetUseCase
import com.example.beep.domain.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MyPageViewModel @Inject constructor(private val presetUseCase: PresetUseCase) :
    ViewModel() {
    private val uid = 5
    val testValue = "Test Value"
    fun printTestValue() {
        println(testValue)
    }

//    val exampleEntities: Flow<Response<List<PresetResponse>>> = PresetUseCase.getUserPresetByToken()

    /*
        StateFlow를 사용하는 방식
    */
//    private val _userMessagePresetList: MutableStateFlow<List<PresetResponse>> = MutableStateFlow(listOf())
//    val userMessagePresetList get() = _userMessagePresetList.value
//
//    private fun getUserMessagePreset() {
//        viewModelScope.launch {
//            getUserMessagePresetUseCase.execute(uid)
//                .collectLatest { _userMessagePresetList.value = it }
//        }
//    }
//    init {
//        getUserMessagePreset()
//    }
}