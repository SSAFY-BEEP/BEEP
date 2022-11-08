package com.example.beep.ui.home

import com.example.beep.domain.retrofit.PatchUserAddressUseCase


import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.mainpage.AddressResponse
import com.example.beep.domain.retrofit.GetUserAddressUseCase
import com.example.beep.domain.retrofit.PostUserAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class PatchAddressViewModel @Inject constructor(private val patchUserAddressUseCase: PatchUserAddressUseCase) :
    ViewModel() {


    fun patchAddress(apiPhone: String, phone: String, name: String){
        viewModelScope.launch(Dispatchers.IO) {
            patchUserAddressUseCase.execute(apiPhone, phone, name).collectLatest {


            }
        }
    }
}
