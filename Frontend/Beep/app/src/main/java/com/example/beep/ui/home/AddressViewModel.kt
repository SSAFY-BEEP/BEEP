package com.example.beep.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.domain.GetUserAddressUseCase
import com.example.beep.ui.mypage.introduce.UiState
import com.example.beep.util.ResultType
import com.example.beep.util.fromJson
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface AddressUiState<out T : Any> {
    data class Success<out T : Any>(val data: T) : AddressUiState<T>
    object Fail : AddressUiState<Nothing>
    object Loading : AddressUiState<Nothing>
}

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getUserAddressUseCase: GetUserAddressUseCase
    ) :
    ViewModel() {
    var addressUiState:AddressUiState<Any> by mutableStateOf(AddressUiState.Loading)

    val gson = Gson()

    //24시간 후에 사라지는 일반 메시지 리스트
    var addressListUiState: UiState<List<AddressResponse>> by mutableStateOf(UiState.Loading)

    suspend fun getAddress() {
        addressListUiState = UiState.Loading
        viewModelScope.launch() {
            getUserAddressUseCase.execute().collectLatest {
                if (it is ResultType.Success) {
                    val list = gson.fromJson<List<AddressResponse>>(gson.toJson(it.data.data))
                    addressListUiState = UiState.Success(list)
                } else {
                    UiState.Error
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

//    init {
//        getAddress()
//    }
}
