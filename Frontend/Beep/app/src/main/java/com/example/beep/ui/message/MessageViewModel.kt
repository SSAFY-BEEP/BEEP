package com.example.beep.ui.message

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.DataModel
import com.example.beep.domain.retrofit.RetrofitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val retrofitUseCase: RetrofitUseCase) :
    ViewModel() {
    val inputName = MutableLiveData<String>()
    val inputJob = MutableLiveData<String>()

    val receivedName = MutableLiveData<String>()
    val receivedJob = MutableLiveData<String>()

    fun postTest(dataModel: DataModel) {
        viewModelScope.launch(Dispatchers.IO) {
            retrofitUseCase.execute(dataModel).collectLatest {
                receivedName.postValue(it.name)
                receivedJob.postValue(it.job)
            }
        }
    }
}