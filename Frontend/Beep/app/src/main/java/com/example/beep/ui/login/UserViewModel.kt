package com.example.beep.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.auth.SignUpRequest
import com.example.beep.domain.LoginUseCase
import com.example.beep.domain.SignUpUseCase
import com.example.beep.util.JWT
import com.example.beep.util.ResultType
import com.example.beep.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    val phoneNumber = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val passwordCheck = MutableLiveData<String>()


    private val _isPasswordChecked = MutableStateFlow("")
    val isPasswordChecked get() = _isPasswordChecked.asStateFlow()

    private val _isJoinChecked = MutableStateFlow(false)
    val isJoinChecked get() = _isJoinChecked.asStateFlow()

    private val _isLoginChecked = MutableStateFlow(false)
    val isLoginChecked get() = _isLoginChecked.asStateFlow()

    val loginEmail = MutableLiveData<String>()

    val loginPassword = MutableLiveData<String>()

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

    // 비밀번호 유효성 검사 실행
//    fun validatePassword(tilPw: TextInputLayout, tilPwCheck: TextInputLayout): Boolean {
//
//        // 비밀번호를 입력했는지 검사
//        if (password.value.isNullOrBlank()) {
//            makeTextInputLayoutError(tilPw, "비밀번호를 입력해주세요")
//            makeToast("비밀번호를 입력해주세요")
//            return false
//        }
//
//        // 비밀번호 형식이 틀린 경우
//        else {
//            val pwRegex = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""
//            val pwPattern = Pattern.compile(pwRegex)
//            if (!pwPattern.matcher(password.value).matches()) {
//                makeTextInputLayoutError(tilPw, "비밀번호를 규칙을 만족하지 못합니다")
//                makeToast("비밀번호를 규칙을 만족하지 못합니다")
//                return false
//            }
//        }
//
//        // 비밀번호 재확인 입력했는지 검사
//        if (passwordCheck.value.isNullOrBlank()) {
//            makeTextInputLayoutError(tilPwCheck, "비밀번호를 입력해주세요")
//            makeToast("비밀번호를 입력해주세요")
//            return false
//        }
//        // 두 비밀번호가 일치하지 않는 경우
//        if (password.value != passwordCheck.value) {
//            makeTextInputLayoutError(tilPwCheck, "비밀번호가 일치하지 않습니다")
//            makeToast("비밀번호가 일치하지 않습니다")
//            return false
//        }
//
//        // 유효성 검사를 다 통과한 경우
//        return true
//    }

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
    fun loginUser(map: HashMap<String, String>) {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.execute(map).collectLatest {
                map["id"] = loginEmail.value!!
                map["pass"] = loginPassword.value!!
                // 서버로 로그인 요청
                if (it is ResultType.Success) {
                    _isLoginChecked.value = true
                    // 토큰값 반환
                    Log.d(TAG, "TOKEN : " + it.data.data.accessToken)
                    _token.value = it.data.data.accessToken
                    Log.d(TAG, "TOKEN : " + _token.value)

                    sharedPreferences.edit().putString(JWT, "Bearer ${it.data.data.accessToken}")
                        .apply()
                } else {
                    Log.d(TAG, "${it}")
                    makeToast("아이디, 비밀번호를 확인해주세요")
                    null
                }
            }
        }
    }
}


