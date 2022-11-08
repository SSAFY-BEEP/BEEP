package com.example.beep.ui.login

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.text.TextLayoutInput
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.auth.LoginRequest
import com.example.beep.data.dto.auth.SignUpRequest
import com.example.beep.di.MainApplication
import com.example.beep.domain.LoginUseCase
import com.example.beep.domain.SignUpUseCase
import com.example.beep.util.Event
import com.example.beep.util.JWT
import com.example.beep.util.ResultType
import com.example.beep.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    val phoneNumber = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val fcmToken = MutableLiveData<String>()
    val passwordCheck = MutableLiveData<String>()


    // viewModel에서 Toast 메시지 띄우기 위한 Event
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    private val _isPasswordChecked = MutableStateFlow("")
    val isPasswordChecked get() = _isPasswordChecked.asStateFlow()

    private val _isJoinChecked = MutableStateFlow(false)
    val isJoinChecked get() = _isJoinChecked.asStateFlow()

    private val _isLoginChecked = MutableStateFlow(false)
    val isLoginChecked get() = _isLoginChecked.asStateFlow()

    private val _joinEvent = SingleLiveEvent<String>()
    val joinEvent: LiveData<String> get() = _joinEvent

    private val _token = MutableStateFlow("")
    val token get() = _token.asStateFlow()

    // 서버에서 받은 인증번호
    private val _emailAuthNumber = MutableStateFlow("")
    val emailAuthNumber get() = _emailAuthNumber.asStateFlow()

    // 사용자에게 입력받은 인증번호
    private val _emailAuthNumberCheck = MutableStateFlow("")
    val emailAuthNumberCheck get() = _emailAuthNumberCheck.asStateFlow()

    private val _fcmEvent = SingleLiveEvent<String>()
    val fcmEvent: LiveData<String> get() = _fcmEvent


    fun validatePassword(tilPw: TextLayoutInput, tilPwCheck: TextLayoutInput): Boolean {
        if (password.value.isNullOrBlank()) {
            makeToast("비밀번호를 입력해주세요")
            return false
        } else if (passwordCheck.value.isNullOrBlank()) {
            makeToast("비밀번호를 입력해주세요")
            return false
        } else if (password.value != passwordCheck.value) {
            makeToast("비밀번호가 일치하지 않습니다")
            return false
        }

        return true
    }

    // 일반 회원가입
    fun signUp() {
        val user = SignUpRequest(
            phoneNumber.value!!,
            password.value!!,
            fcmToken.value!!
        )
        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase.execute(user).collectLatest {
                if (it is ResultType.Success) {
                    _isJoinChecked.value = true
                }
            }
        }
    }

    // 일반 로그인
    fun loginUser() {
        val user = LoginRequest(
            phoneNumber.value!!,
            password.value!!
        )
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.execute(user).collectLatest {
                // 서버로 로그인 요청
                if (it is ResultType.Success) {
                    _isLoginChecked.value = true
                    _token.value = it.data.toString()
                    sharedPreferences.edit().putString(JWT, "Bearer ${it.data}")
                        .apply()
                } else {
                    null
                }
            }
        }
    }

    fun makeToast(msg: String) {
        _message.postValue(Event(msg))
    }


}


