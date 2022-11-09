package com.example.beep.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.auth.LoginRequest
import com.example.beep.data.dto.auth.SignUpRequest
import com.example.beep.di.MainApplication
import com.example.beep.domain.LoginUseCase
import com.example.beep.domain.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val loginUseCase: LoginUseCase,
//    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun signUp(
        phoneNumber: String,
        password1: String,
        password2: String
    ) {
        val fcmToken = MainApplication.sharedPreferencesUtil.getFcmToken()

        val request =
            SignUpRequest(phoneNumber = phoneNumber, password = password1, fcmToken = fcmToken!!)
        println(fcmToken)

        viewModelScope.launch(Dispatchers.IO) {
            signUpUseCase.execute(request).collectLatest {
                if (it.phoneNumber.isNotBlank()) {
                    login(it.phoneNumber, it.password)
                }

            }
        }
    }

    fun validatePassword(password: String, passwordCheck: String): Boolean {
        if (password.isBlank()) {
            return false
        }

        if (passwordCheck.isBlank()) {
            return false
        }

        if (password != passwordCheck) {
            return false
        }
        return true
    }

    fun login(
        phoneNumber: String,
        password: String,
    ) {

        val request =
            LoginRequest(phoneNumber = phoneNumber, password = password)

        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase.execute(request).collectLatest {
                if (it.token.isNotBlank()) {
                    MainApplication.sharedPreferencesUtil.saveToken(it.token)
                }
            }
        }
    }
}


