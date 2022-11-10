package com.example.beep.ui.home

import com.example.beep.domain.retrofit.DeleteUserAddressUseCase



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressDeleteViewModel @Inject constructor(private val deleteUserAddressUseCase: DeleteUserAddressUseCase) :
    ViewModel() {


    fun deleteAddress(phone: String){
        viewModelScope.launch(Dispatchers.IO) {
            deleteUserAddressUseCase.execute(phone).collectLatest {


            }
        }
    }
}
