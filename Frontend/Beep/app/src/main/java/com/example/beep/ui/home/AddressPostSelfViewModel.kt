package com.example.beep.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.domain.retrofit.PostUserAddressUseCase
import com.example.beep.util.ResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressPostSelfViewModel @Inject constructor(private val postUserAddressUseCase: PostUserAddressUseCase) :
    ViewModel() {

//    var addressPostStatus: ResultType<> by mutableStateOf(ResultType.Loading)

    fun postAddress(phone: String, name: String){
        viewModelScope.launch(Dispatchers.IO) {
//            postUserAddressUseCase.execute(phone, name).collectLatest {
//                if (it is ResultType.Success) {
//                    Log.d("성공","$it")
////                    addressPostStatus = ResultType.Success(it.data)
//                } else {
//                    Log.d("실패", "$it")
////                    addressPostStatus = ResultType.Fail
//                }
//                Log.d("Phone", phone)
//                Log.d("Name", name)
////                Log.d("POST ADDRESS", it)
//
//            }
        }
    }
}
