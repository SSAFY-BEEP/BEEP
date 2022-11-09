package com.example.beep.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.domain.retrofit.PostUserAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressPostSelfViewModel @Inject constructor(private val postUserAddressUseCase: PostUserAddressUseCase) :
    ViewModel() {

    fun postAddress(phone: String, name: String){
        viewModelScope.launch(Dispatchers.IO) {
            postUserAddressUseCase.execute(phone, name).collectLatest {
                Log.d("Phone", phone)
                Log.d("Name", name)
                Log.d("POST ADDRESS", it)
//                if(it is ResultType.Success){
//                    _successMsgEvent.postValue("성공")
//                }else if(it is ResultType.Fail){
//                    _failMsgEvent.postValue("실패")
//                }
            }
        }
    }
}
