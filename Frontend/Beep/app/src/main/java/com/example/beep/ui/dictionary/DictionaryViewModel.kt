package com.example.beep.ui.dictionary

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.dictionary.DictionaryResponse
import com.example.beep.domain.DictionaryUseCase
import com.example.beep.ui.mypage.introduce.UiState
import com.example.beep.util.ResultType
import com.example.beep.util.fromJson
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val dictionaryUseCase: DictionaryUseCase
) : ViewModel() {
    var dictionaryState : UiState<List<DictionaryResponse>> by mutableStateOf(UiState.Loading)
    var searchWord by mutableStateOf("")
    var searchedState by mutableStateOf(false)

    val gson = Gson()

    fun getDictionary() {
        dictionaryState = UiState.Loading
        viewModelScope.launch {
            val result = dictionaryUseCase.getDictionary(searchWord)
            result.collectLatest {
                dictionaryState = when(it) {
                    is ResultType.Success -> {
                        var list = gson.fromJson<List<DictionaryResponse>>(gson.toJson(it.data.data))
                        Log.d("Get Dictionary By word", list.toString())
                        UiState.Success(list)
                    }
                    is ResultType.Loading -> {
                        UiState.Loading
                    } else -> {
                        UiState.Error
                    }
                }
            }
        }
    }

    init {
        getDictionary()
    }
}