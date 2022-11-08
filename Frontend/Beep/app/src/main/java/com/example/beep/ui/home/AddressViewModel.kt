package com.example.beep.ui.home

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.domain.retrofit.GetUserAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(private val getUserAddressUseCase: GetUserAddressUseCase) :
    ViewModel() {

         val exampleEntities: Flow<List<AddressResponse>> = getUserAddressUseCase.execute()

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
}
