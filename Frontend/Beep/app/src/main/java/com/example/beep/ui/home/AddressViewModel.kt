package com.example.beep.ui.home

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.domain.retrofit.GetUserAddressUseCase
import com.example.beep.util.ResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AddressUiState<out T : Any> {
    data class Success<out T : Any>(val data: T) : AddressUiState<T>
    object Fail : AddressUiState<Nothing>
    object Loading : AddressUiState<Nothing>
}

@HiltViewModel
class AddressViewModel @Inject constructor(private val getUserAddressUseCase: GetUserAddressUseCase) :
    ViewModel() {
    var addressUiState:AddressUiState<Any> by mutableStateOf(AddressUiState.Loading)


    fun getAddress(){
        viewModelScope.launch(Dispatchers.Main) {
            addressUiState = AddressUiState.Loading
            getUserAddressUseCase.execute().collectLatest {
                addressUiState = if(it is ResultType.Success){
                    Log.d("성공","$it")
                    AddressUiState.Success(it.data.data)
                }else{
                    Log.d("실패", "$it")
                    AddressUiState.Fail
                }
            }
        }
    }

//         val exampleEntities: Flow<List<AddressResponse>> = getUserAddressUseCase.execute()

//        StateFlow 사용하는 방식
//    init {
//        getUserMessagePreset()
//    }
//
//    private val _userAddressList: MutableStateFlow<List<AddressResponse>> = MutableStateFlow(listOf())
//    val userAddressList get() = _userAddressList.value
//
//    private fun getUserMessagePreset() {
//        viewModelScope.launch {
//            getUserAddressUseCase.execute()
//                .collectLatest { _userAddressList.value = it }
//        }
//    }

    init {
        getAddress()
    }
}
