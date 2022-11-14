package com.example.beep.ui.home

import com.example.beep.domain.retrofit.PatchUserAddressUseCase


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressPatchViewModel @Inject constructor(private val patchUserAddressUseCase: PatchUserAddressUseCase) :
    ViewModel() {


    fun patchAddress(apiPhone: String, phone: String, name: String){
        viewModelScope.launch(Dispatchers.IO) {
//            patchUserAddressUseCase.execute(apiPhone, phone, name).collectLatest {
//
//
//            }
        }
    }
}
